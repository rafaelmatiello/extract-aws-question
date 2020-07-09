package net.matiello.simuladoawsbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.matiello.simuladoawsbackend.document.AnswerDocument;
import net.matiello.simuladoawsbackend.document.QuestionDocument;
import net.matiello.simuladoawsbackend.pojo.ReportItem;
import net.matiello.simuladoawsbackend.repository.AnswerRepository;
import net.matiello.simuladoawsbackend.repository.QuestionRepository;

@RestController()
public class ReportController {

	@Autowired
	MongoOperations mongoOps;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping("/reports/{id}")
	public List<ReportItem> index(@PathVariable(name = "id") Integer chapter) {

		List<QuestionDocument> question = questionRepository.findAllByChapter(chapter);

		Map<Integer, QuestionDocument> mapQuestion = question.stream()
				.collect(Collectors.toMap(QuestionDocument::getIndex, q -> q));

		List<AnswerDocument> answer = answerRepository.findAllByChapter(chapter);

		Map<Integer, AnswerDocument> mapAnsewer = answer.stream()
				.collect(Collectors.toMap(AnswerDocument::getIndex, aAnswer -> aAnswer));

		List<ReportItem> report = new ArrayList<ReportItem>();

		for (Entry<Integer, QuestionDocument> entryQuestion : mapQuestion.entrySet()) {
			ReportItem newReport = new ReportItem();
			newReport.setQuestion(entryQuestion.getValue());
			newReport.setAnswer(mapAnsewer.get(entryQuestion.getKey()));
			report.add(newReport);
		}

		return report;
	}

}
