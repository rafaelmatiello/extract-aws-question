package net.matiello.simuladoawsbackend.document;

public class AnswerOption {
	private String option;
	private String description;

	public AnswerOption() {
	}

	public String getOption() {
		return option;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return option + "." + description+"\n";
	}

	public void setOption(String option) {
		this.option = option;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
