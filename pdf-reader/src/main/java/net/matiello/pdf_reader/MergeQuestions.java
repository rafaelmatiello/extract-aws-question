package net.matiello.pdf_reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.matiello.pdf_reader.pojo.Question;

public class MergeQuestions {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();

		File resultFile = new File(
				"C:\\repositorios\\extract-aws-question\\pdf-reader\\src\\main\\resources\\perguntasImportacao.json");

		List<String> files = new ArrayList<String>();
		files.add("C:\\repositorios\\extract-aws-question\\pdf-reader\\src\\main\\resources\\perguntas.json");
		files.add("C:\\repositorios\\extract-aws-question\\pdf-reader\\src\\main\\resources\\perguntas_udemy.json");

		List<Question> allQuestion = new ArrayList<Question>();
		for (String file : files) {
			allQuestion.addAll(readQuestion(file, objectMapper));
		}

		objectMapper.writerWithDefaultPrettyPrinter().writeValue(resultFile, allQuestion);
		System.out.println("Finalizado!");
	}

	private static List<Question> readQuestion(String fileName, ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {

		File f1 = new File(fileName);
		return objectMapper.readValue(f1, new TypeReference<List<Question>>() {
		});
	}
}
