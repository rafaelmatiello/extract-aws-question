package net.matiello.simuladoawsbackend.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.matiello.simuladoawsbackend.document.AnswerDocument;
import net.matiello.simuladoawsbackend.pojo.Statistics;
import net.matiello.simuladoawsbackend.repository.AnswerRepository;

@RestController()
public class StatisticsController {

	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping("/statistics")
	public Collection<Statistics> getStatistics() {

		List<AnswerDocument> allAnswer = answerRepository.findAll();

		Map<String, Statistics> list = new HashMap<String, Statistics>();

		allAnswer.stream().forEach(answer -> {
			Statistics statistics = list.computeIfAbsent(String.valueOf(answer.getChapter()), key -> {
				return new Statistics(key);
			});

			statistics.setAnswers(statistics.getAnswers() + 1);

			if (answer.getCorrect()) {
				statistics.setCorrect(statistics.getCorrect() + 1);
			}

			if (!answer.getCorrect()) {
				statistics.setWrong(statistics.getWrong() + 1);
			}

		});

		return list.values();
	}

}
