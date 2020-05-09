package net.matiello.simuladoawsbackend.controller;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.matiello.simuladoawsbackend.document.AnswerDocument;
import net.matiello.simuladoawsbackend.document.QuestionDocument;
import net.matiello.simuladoawsbackend.repository.AnswerRepository;
import net.matiello.simuladoawsbackend.repository.QuestionRepository;

@RestController()
public class QuestionController {

	@Autowired
	private QuestionRepository questionDocumentRespository;

	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping("/questions")
	public Iterable<QuestionDocument> index() {
		return questionDocumentRespository.findAll();
	}

	@GetMapping("/questions/count")
	public long count() {
		return questionDocumentRespository.count();
	}

	@GetMapping("/questions/wrong")
	public long wrong() {
		List<AnswerDocument> result = answerRepository.findAllByCorrect(false);
		if(!result.isEmpty()) {
			int randomNum = ThreadLocalRandom.current().nextInt(1, result.size());
			AnswerDocument answerDocument = result.get(randomNum);
			return answerDocument.getIndex();
		}
		
		return  ThreadLocalRandom.current().nextInt(1, (int) questionDocumentRespository.count());
	}

	@GetMapping("/questions/{id}")
	public QuestionDocument getById(@PathVariable(name = "id") String index) {
		return questionDocumentRespository.findByIndex(Integer.parseInt(index));
	}

	@PostMapping("/questions")
	public QuestionDocument create(@RequestBody QuestionDocument question) {
		return questionDocumentRespository.save(question);
	}

	@PostMapping("/questions/all")
	public List<QuestionDocument> createAll(@RequestBody List<QuestionDocument> question) {
		return questionDocumentRespository.saveAll(question);
	}

	@DeleteMapping("/questions/all")
	public long deleteAll() {
		long count = questionDocumentRespository.count();
		questionDocumentRespository.deleteAll();
		return count;
	}

}
