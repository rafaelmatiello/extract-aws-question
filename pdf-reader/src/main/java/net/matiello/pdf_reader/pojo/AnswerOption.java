package net.matiello.pdf_reader.pojo;

public class AnswerOption {
	private String option;
	private String description;

	public AnswerOption(String option, String description) {
		super();
		this.option = option;
		this.description = description;
	}

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
