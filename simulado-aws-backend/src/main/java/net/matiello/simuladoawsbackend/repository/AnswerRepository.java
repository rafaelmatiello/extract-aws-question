package net.matiello.simuladoawsbackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.matiello.simuladoawsbackend.document.AnswerDocument;

public interface AnswerRepository extends MongoRepository<AnswerDocument, String> {

	AnswerDocument findByIndex(Integer index);

	List<AnswerDocument> findAllByCorrect(Boolean correct);

	List<AnswerDocument> findAllByChapter(int chapter);
}
