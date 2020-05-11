package net.matiello.pdf_reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matiello.udemy.pojo.Prompt;
import com.matiello.udemy.pojo.Result;
import com.matiello.udemy.pojo.Session;

import net.matiello.pdf_reader.pojo.AnswerOption;
import net.matiello.pdf_reader.pojo.Question;
import net.matiello.pdf_reader.pojo.RightAnswer;
import net.matiello.pdf_reader.utils.IndexCount;
import net.matiello.pdf_reader.utils.LeterOption;

public class ReadUdemy {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ReadUdemy readUdemy = new ReadUdemy();

		File root = new File(
				"C:\\repositorios\\extract-aws-question\\pdf-reader\\src\\main\\resources\\udemy_simulate_acloud");

		File fileDestination = new File(
				"C:\\repositorios\\extract-aws-question\\pdf-reader\\src\\main\\resources\\perguntas_udemy.json");

		File[] listFiles = root.listFiles();
		IndexCount index = new IndexCount(1006);

		Map<Integer, File> maps = new HashMap<Integer, File>();

		for (File fileSource : listFiles) {
			int chapter = Integer.parseInt(fileSource.getName().split("_")[0]);
			maps.put(10 + chapter, fileSource);
		}

		List<Integer> keys = new ArrayList<>(maps.keySet());
		Collections.sort(keys);

		List<Question> questions = new ArrayList<Question>();
		for (Integer chapter : keys) {

			File source = maps.get(chapter);
			System.out.println(source);
			questions.addAll(readUdemy.process(chapter, index, source));

		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileDestination, questions);

	}

	public List<Question> process(int chapter, IndexCount index, File source)
			throws IOException, JsonParseException, JsonMappingException, JsonGenerationException {

		ObjectMapper objectMapper = new ObjectMapper();
		Session session = objectMapper.readValue(source, Session.class);

		int number = 1;

		return converterToQuestion(session, index, number, chapter);

	}

	private static List<Question> converterToQuestion(Session session, IndexCount index, int number, int chapter) {
		List<Question> questions = new ArrayList<Question>();
		LeterOption letter = new LeterOption();

		for (Result result : session.getResults()) {

			Question question = new Question();
			letter.reset();
			Prompt prompt = result.getPrompt();

			question.setChapter(chapter);

			question.setIndex(index.next());
			question.setNumber(number++);

			question.setAsking(html2text(prompt.getQuestion()));

			List<AnswerOption> answers = new ArrayList<AnswerOption>();
			prompt.getAnswers().stream().forEach(proptAnswers -> {
				answers.add(new AnswerOption(letter.next(), html2text(proptAnswers)));
			});
			question.setAnswerOption(answers);

			letter.reset();

			if (prompt.getFeedbacks() != null) {
				prompt.getFeedbacks().stream().forEach(feedbacks -> {
					String nextLeter = letter.next();
					if (!feedbacks.isEmpty()) {
						question.getFeedbacks().add(new AnswerOption(nextLeter, html2text(feedbacks)));
					}
				});
			}

			RightAnswer rightAnswer = new RightAnswer();

			List<String> correctAnswerUpper = result.getCorrectResponse().stream().map(option -> option.toUpperCase())
					.collect(Collectors.toList());
			rightAnswer.setDomain(question.getChapter());
			rightAnswer.setNumber(question.getNumber());
			rightAnswer.setDescription(html2text(prompt.getExplanation()));

			rightAnswer.setOptions(correctAnswerUpper);

			question.setRightAnswer(rightAnswer);

			questions.add(question);
		}
		return questions;
	}

	public static String html2text(String html) {
		if (html == null || html.equals("")) {
			return "";
		}
		// boolean strictMode = true;
		// return org.jsoup.parser.Parser.unescapeEntities(html, strictMode);
		return Jsoup.parse(html).text();
	}
}
