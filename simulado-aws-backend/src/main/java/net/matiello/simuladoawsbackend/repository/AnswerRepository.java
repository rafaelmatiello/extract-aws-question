package net.matiello.simuladoawsbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.matiello.simuladoawsbackend.document.AnswerDocument;

public interface AnswerRepository extends MongoRepository<AnswerDocument, String> {

	AnswerDocument findByIndex(Integer index);
}
