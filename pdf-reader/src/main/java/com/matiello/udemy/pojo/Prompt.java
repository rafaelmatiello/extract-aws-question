package com.matiello.udemy.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "feedbacks", "relatedLectureIds", "answers", "question" })
public class Prompt {

	@JsonProperty("feedbacks")
	private List<String> feedbacks = null;

	@JsonProperty("relatedLectureIds")
	private List<String> relatedLectureIds;

	@JsonProperty("answers")
	private List<String> answers = null;

	@JsonProperty("question")
	private String question;

	@JsonProperty("explanation")
	private String explanation;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("feedbacks")
	public List<String> getFeedbacks() {
		return feedbacks;
	}

	@JsonProperty("feedbacks")
	public void setFeedbacks(List<String> feedbacks) {
		this.feedbacks = feedbacks;
	}

	@JsonProperty("relatedLectureIds")
	public List<String> getRelatedLectureIds() {
		return relatedLectureIds;
	}

	@JsonProperty("relatedLectureIds")
	public void setRelatedLectureIds(List<String> relatedLectureIds) {
		this.relatedLectureIds = relatedLectureIds;
	}

	@JsonProperty("answers")
	public List<String> getAnswers() {
		return answers;
	}

	@JsonProperty("answers")
	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	@JsonProperty("question")
	public String getQuestion() {
		return question;
	}

	@JsonProperty("question")
	public void setQuestion(String question) {
		this.question = question;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "Prompt [feedbacks=" + feedbacks + ", relatedLectureIds=" + relatedLectureIds + ", answers=" + answers
				+ ", question=" + question + ", additionalProperties=" + additionalProperties + "]";
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

}
