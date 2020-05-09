package net.matiello.pdf_reader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.io.IOUtils;
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
		String fileName = "C:\\workspace\\estudos\\pdf-reader\\src\\main\\resources\\bookPdf.pdf";
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
			int lastDomain = 0;

			boolean readQuestion = true;

			RightAnswer currentRightAnswer = null;
			boolean startRightAnswer = false;

			int index = 1;
			// print lines
			for (String line : lines) {
				System.out.println(line);

				// inicia os capitulos
				if (line.equals("AWS Certified Solutions Architect Practice Tests: Associate SAA-C01 Exam ")) {
					currentQuestion = null;
					currentAnswer = null;
					startAnswer = false;
					startQuestion = false;
					continue;
				}

				if (line.equals("182 Appendix ■ Answers to Review Questions")) {
					readQuestion = false;
					continue;
				}

				if (readQuestion) {

					Integer chapter = isChapter(line);
					if (chapter != null) {
						lastChapter = chapter;
						continue;
					}
					

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
						currentQuestion.setAsking(currentQuestion.getAsking() + line);
					}

					if (answer != null) {
						currentAnswer = answer;
						startQuestion = false;
						startAnswer = true;
						currentQuestion.getAnswerOption().add(answer);
					} else if (startAnswer) {
						
						if(isReviewQuestion(line)) {
							continue;
						}
						
						currentAnswer.setDescription(currentAnswer.getDescription() + line);
					}
				} else {
					if (isAppendix(line)) {
						continue;
					}
					
					if(isIndexTitle(line)) {
						break;
					}

					Integer domain = isDomain(line);
					if (domain != null) {
						lastDomain = domain;
						startRightAnswer = false;
					}
					
					
					if(lastDomain == 5) {
						if(isPracticeTest(line)) {
							lastDomain++;
							startRightAnswer = false;
						}
					}
					

					RightAnswer rightAnswer = isRightAnswer(line);

					if (rightAnswer != null) {
						currentRightAnswer = rightAnswer;
						currentRightAnswer.setDomain(lastDomain);
						startRightAnswer = true;
						
						Question question = questions.get(lastDomain + "-" + rightAnswer.getNumber());
						if(question == null) {
							questionNotFound.add(lastDomain + "-" + rightAnswer.getNumber());
						}else {
							question.setRightAnswer(currentRightAnswer);
						}
					} else if (startRightAnswer) {
						
						if(isDomainWithNumberPage(line)) {
							continue;
						}
						currentRightAnswer.setDescription(currentRightAnswer.getDescription() + line);
					}

				}
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File("C:\\repositorios\\extract-aws-question\\pdf-reader\\src\\main\\resources\\perguntas.json"), questions.values());

		
		objectMapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File("C:\\repositorios\\extract-aws-question\\pdf-reader\\src\\main\\resources\\notFound.json"), questionNotFound);

	}

	private static AnswerOption isAnswerOptions(String line) {
		Pattern pattern = Pattern.compile("^(A|B|C|D|E)\\.\\s*(.*)$");

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

		Pattern pattern = Pattern.compile("[0-9]+\\s(Chapter)\\s([0-9])\\s.*");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return Integer.parseInt(matcher.group(2));
		}

		return null;
	}

	private static Integer isDomain(String line) {

		//Pattern pattern = Pattern.compile("^Domain\\s([0-9|\\s]+)\\:\\s([^0-9]*)\\s?(\\d*)$");
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

		Pattern pattern = Pattern.compile("^\\s?([0-9|\\s]+)\\.\\s([\\w|,|\\s]*).\\s(.*)$");
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {

			RightAnswer rightAnswer = new RightAnswer();
			rightAnswer.setNumber(Integer.parseInt(matcher.group(1).replace(" ", "")));

			String[] answer = matcher.group(2).split(",");
			for (int i = 0; i < answer.length; i++) {
				rightAnswer.getOptions().add(answer[i].replace(" ", ""));
			}
			rightAnswer.setDescription(matcher.group(3));
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
		lines.add(str);
	}
}