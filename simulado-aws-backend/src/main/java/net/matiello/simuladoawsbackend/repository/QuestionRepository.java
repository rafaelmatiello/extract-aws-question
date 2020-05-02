package net.matiello.simuladoawsbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.matiello.simuladoawsbackend.document.QuestionDocument;

public interface QuestionRepository extends MongoRepository<QuestionDocument, String> {

	QuestionDocument findByIndex(Integer index);
}
