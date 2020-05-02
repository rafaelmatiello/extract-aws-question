package net.matiello.simuladoawsbackend.document;

import java.util.ArrayList;
import java.util.List;

public class RightAnswer {
	private int domain;
	private int number;
	private List<String> options = new ArrayList<String>(1);
	private String description;

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return domain + "-" + number+": "+ options + ":" + description+"\n";
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getDomain() {
		return domain;
	}

	public void setDomain(int domain) {
		this.domain = domain;
	}

}
