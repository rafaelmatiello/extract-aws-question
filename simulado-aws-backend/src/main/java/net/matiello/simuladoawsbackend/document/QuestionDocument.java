package net.matiello.simuladoawsbackend.document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "question")
public class QuestionDocument {

	@Id
	private String id;
	private int index;
	private int chapter;
	private int number;
	private String asking;

	private List<AnswerOption> answerOption = new ArrayList<AnswerOption>(4);

	private RightAnswer rightAnswer;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getAsking() {
		return asking;
	}

	public void setAsking(String asking) {
		this.asking = asking;
	}

	@Override
	public String toString() {
		return number + ". " + asking + "\n" + getAnswerOption();
	}

	public List<AnswerOption> getAnswerOption() {
		return answerOption;
	}

	public void setAnswerOption(List<AnswerOption> answers) {
		this.answerOption = answers;
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public RightAnswer getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(RightAnswer rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
