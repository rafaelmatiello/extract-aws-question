package net.matiello.simuladoawsbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.matiello.simuladoawsbackend.document.SessionDocument;

public interface SessionRepository extends MongoRepository<SessionDocument, String> {

}
