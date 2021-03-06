package net.matiello.pdf_reader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.matiello.pdf_reader.pojo.AnswerOption;
import net.matiello.pdf_reader.pojo.Question;
import net.matiello.pdf_reader.pojo.RightAnswer;

/**
 * This is an example on how to extract text line by line from pdf document
 */
public class ReadAwsBook extends PDFTextStripper {

	static List<String> lines = new ArrayList<String>();

	public ReadAwsBook() throws IOException {
	}

	/**
	 * @throws IOException If there is an error parsing the document.
	 */
	public static void main(String[] args) throws IOException {
		PDDocument document = null;
		String fileName = "C:\\Users\\rafael\\OneDrive\\aws\\livro aws\\AWS-Certified-Solutions-Architect-Official-Study-Guide.pdf.pdf";
		Map<String, Question> questions = new LinkedHashMap<String, Question>();
		List<String> questionNotFound = new ArrayList<String>();
		try {

			document = PDDocument.load(new File(fileName));
			PDFTextStripper stripper = new ReadAwsBook();
			stripper.setSortByPosition(true);
			stripper.setStartPage(0);
			stripper.setEndPage(document.getNumberOfPages());
			// stripper.setEndPage(59);
			Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream(), "UTF-8");
			stripper.writeText(document, dummy);

			Question currentQuestion = null;
			AnswerOption currentAnswer = null;

			boolean startQuestion = false;
			boolean startAnswer = false;
			int lastChapter = 0;
			int ansNumber = 0;
			int lastDomain = 0;

			boolean readQuestion = true;

			RightAnswer currentRightAnswer = null;
			boolean startRightAnswer = false;

			boolean startParseQuestion = false;
			int index = 1;
			// print lines
			for (String line : lines) {
				System.out.println(line);

				// inicia os capitulos
				if (line.equals("Review Questions")) {
					startParseQuestion = true;
					currentQuestion = null;
					currentAnswer = null;
					startAnswer = false;
					startQuestion = false;
					continue;
				}

				if (isChapter(line) != null && startParseQuestion) {
					lastChapter = isChapter(line);
					if (lastChapter == 1) {
						questions.clear();
					}
					startParseQuestion = false;
					continue;
				}

				if (line.equals("Answers to Review Questions")) {
					readQuestion = false;
					continue;
				}

				if (readQuestion) {
					if (startParseQuestion) {
						Question question = isQuestion(line);
						AnswerOption answer = isAnswerOptions(line);

						if (question == null && currentQuestion == null && currentAnswer == null) {
							continue;
						}

						if (question != null) {
							currentQuestion = question;
							currentQuestion.setChapter(lastChapter);
							question.setIndex(index++);
							startQuestion = true;
							startAnswer = false;
							questions.put(lastChapter + "-" + question.getNumber(), question);
						} else if (startQuestion && answer == null) {
							currentQuestion.setAsking(currentQuestion.getAsking() + " " + line);
						}

						if (answer != null) {
							currentAnswer = answer;
							startQuestion = false;
							startAnswer = true;
							currentQuestion.getAnswerOption().add(answer);
						} else if (startAnswer) {

							if (isReviewQuestion(line)) {
								continue;
							}

							currentAnswer.setDescription(currentAnswer.getDescription() + " " + line);
						}
					}
				} else {

					if (isChapterAns(line) != null) {
						lastChapter = isChapterAns(line);
						startRightAnswer = false;
						continue;
					}

					if (isNumberAns(line) != null) {
						ansNumber = isNumberAns(line);
						startRightAnswer = false;
						continue;
					}

					RightAnswer rightAnswer = isRightAnswer(line);

					if (rightAnswer != null) {
						rightAnswer.setNumber(ansNumber);
						currentRightAnswer = rightAnswer;
						currentRightAnswer.setDomain(lastChapter);
						startRightAnswer = true;

						Question question = questions.get(lastChapter + "-" + rightAnswer.getNumber());
						if (question == null) {
							questionNotFound.add(lastChapter + "-" + rightAnswer.getNumber());
						} else {
							question.setRightAnswer(currentRightAnswer);
						}
					} else if (startRightAnswer) {

						if (isDomainWithNumberPage(line)) {
							continue;
						}
						currentRightAnswer.setDescription(currentRightAnswer.getDescription() + " " + line);
					}

				}
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();
		List<Question> values = new ArrayList<Question>(questions.values());
		Collections.sort(values, new Comparator<Question>() {

			@Override
			public int compare(Question o1, Question o2) {

				int compareTo = Integer.valueOf(o1.getChapter()).compareTo(o2.getChapter());
				if (compareTo != 0) {
					return compareTo;
				}

				return Integer.valueOf(o1.getNumber()).compareTo(o2.getNumber());
			}

		});

		int index = 1301;
		for (Question question : values) {
			question.setIndex(index++);
			question.setChapter(question.getChapter() + 30);
		}

		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(
				"C:\\repositorios\\extract-aws-question\\pdf-reader\\src\\main\\resources\\perguntas_oficial.json"),
				values);

		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(
				"C:\\repositorios\\extract-aws-question\\pdf-reader\\src\\main\\resources\\notFound_oficial.json"),
				questionNotFound);

	}

	private static AnswerOption isAnswerOptions(String line) {
		Pattern pattern = Pattern.compile("^(A|B|C|D|E|F|G)\\.\\s*(.*)$");

		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			AnswerOption ans = new AnswerOption();
			ans.setOption(matcher.group(1));
			ans.setDescription(matcher.group(2));

			return ans;
		}

		return null;
	}

	private static Question isQuestion(String line) {

		Pattern pattern = Pattern.compile("^\\s?([0-9|\\s]+)\\.\\s(.*)$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			Question q = new Question();
			q.setNumber(Integer.parseInt(matcher.group(1).replace(" ", "")));
			q.setAsking(matcher.group(2));

			return q;
		}

		return null;
	}

	private static Integer isChapter(String line) {

		Pattern pattern = Pattern.compile("(Chapter)\\s([0-9]+)\\s.*");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return Integer.parseInt(matcher.group(2));
		}

		return null;
	}

	private static Integer isChapterAns(String line) {

		Pattern pattern = Pattern.compile("(Chapter)\\s([0-9]+):\\s.*");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return Integer.parseInt(matcher.group(2));
		}

		return null;
	}

	private static Integer isNumberAns(String line) {

		Pattern pattern = Pattern.compile("^([0-9]+).\\s*$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}

		return null;
	}

	private static Integer isDomain(String line) {

		// Pattern pattern =
		// Pattern.compile("^Domain\\s([0-9|\\s]+)\\:\\s([^0-9]*)\\s?(\\d*)$");
		Pattern pattern = Pattern.compile("^Domain\\s([0-9|\\s]+)\\:\\s([^0-9]*)$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1).replace(" ", ""));
		}

		return null;
	}

	private static boolean isDomainWithNumberPage(String line) {

		Pattern pattern = Pattern.compile("^Domain\\s([0-9|\\s]+)\\:\\s([^0-9]*)\\s?(\\d*)$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	private static RightAnswer isRightAnswer(String line) {

		Pattern pattern = Pattern.compile("^([A-F|,|\\s]+).\\s(.*)$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {

			RightAnswer rightAnswer = new RightAnswer();

			String[] answer = matcher.group(1).split(",");
			for (int i = 0; i < answer.length; i++) {
				rightAnswer.getOptions().add(answer[i].replace(" ", ""));
			}
			rightAnswer.setDescription(matcher.group(2));
			return rightAnswer;
		}

		return null;
	}

	private static boolean isIndexTitle(String line) {

		Pattern pattern = Pattern.compile("^Index$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	private static boolean isAppendix(String line) {

		Pattern pattern = Pattern.compile("[0-9]+\\s(Appendix)\\s.*");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	private static boolean isReviewQuestion(String line) {

		Pattern pattern = Pattern.compile("Review Questions\\s[0-9]+");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	private static boolean isPracticeTest(String line) {

		Pattern pattern = Pattern.compile("^Practice Test$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	@Override
	protected void writeString(String str, List<TextPosition> textPositions) throws IOException {
		lines.add(str.replace("\t", " "));
	}
}