package net.matiello.simuladoawsbackend.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.matiello.simuladoawsbackend.document.AnswerDocument;
import net.matiello.simuladoawsbackend.repository.AnswerRepository;

@RestController()
public class AnswersController {

	@Autowired
	MongoOperations mongoOps;

	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping("/answers")
	public Iterable<AnswerDocument> index() {
		return answerRepository.findAll();
	}

	@PostMapping("/answers/{id}")
	public AnswerDocument updateById(@PathVariable(name = "id") Integer index,
			@RequestBody AnswerDocument answerEntity) {

		AnswerDocument answerDocument = answerRepository.findByIndex(index);
		if (answerDocument != null) {
			answerDocument.setSelectOption(answerEntity.getSelectOption());
			answerDocument.setCorrect(answerEntity.getCorrect());
		} else {
			answerDocument = answerEntity;
		}
		answerDocument.setDate(LocalDateTime.now());

		return answerRepository.save(answerDocument);

	}

	@DeleteMapping("/answers/all")
	public long deleteAll() {
		long count = answerRepository.count();
		answerRepository.deleteAll();
		return count;
	}
}
