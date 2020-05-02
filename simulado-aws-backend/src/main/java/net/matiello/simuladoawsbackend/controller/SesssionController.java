package net.matiello.simuladoawsbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.matiello.simuladoawsbackend.document.SessionDocument;
import net.matiello.simuladoawsbackend.repository.SessionRepository;

@RestController()
public class SesssionController {

	@Autowired
	private SessionRepository sessionRepository;

	@GetMapping("/session")
	public SessionDocument get() {
		
		List<SessionDocument> findAll = sessionRepository.findAll();
		
		if(findAll != null && !findAll.isEmpty()) {
			return findAll.get(0);
		}
		SessionDocument sessionDocument = new SessionDocument();
		sessionDocument.setLastIndex(1);
		
		return sessionRepository.save(sessionDocument);
	}
	
	
	@PostMapping("/session")
	public SessionDocument create(@RequestBody SessionDocument sessionDocument) {
		return sessionRepository.save(sessionDocument);
	}
	

}
