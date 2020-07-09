package net.matiello.simuladoawsbackend.pojo;

import net.matiello.simuladoawsbackend.document.AnswerDocument;
import net.matiello.simuladoawsbackend.document.QuestionDocument;

public class ReportItem {

	private QuestionDocument question;

	private AnswerDocument answer;

	public QuestionDocument getQuestion() {
		return question;
	}

	public void setQuestion(QuestionDocument question) {
		this.question = question;
	}

	public AnswerDocument getAnswer() {
		return answer;
	}

	public void setAnswer(AnswerDocument answer) {
		this.answer = answer;
	}

}
