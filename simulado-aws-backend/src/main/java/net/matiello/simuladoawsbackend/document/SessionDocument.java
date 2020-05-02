package net.matiello.simuladoawsbackend.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "session")
public class SessionDocument {
	
	@Id
	private String id;
	
	private Integer lastIndex;

	public Integer getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(Integer lastIndex) {
		this.lastIndex = lastIndex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
