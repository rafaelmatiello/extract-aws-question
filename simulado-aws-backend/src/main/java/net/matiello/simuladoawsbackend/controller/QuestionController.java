package net.matiello.simuladoawsbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.matiello.simuladoawsbackend.document.QuestionDocument;
import net.matiello.simuladoawsbackend.repository.QuestionRepository;

@RestController()
public class QuestionController {

	@Autowired
	private QuestionRepository questionDocumentRespository;

	@GetMapping("/questions")
	public Iterable<QuestionDocument> index() {
		return questionDocumentRespository.findAll();
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
