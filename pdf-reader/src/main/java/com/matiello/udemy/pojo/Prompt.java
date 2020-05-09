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
	private String relatedLectureIds;
	@JsonProperty("answers")
	private List<String> answers = null;
	@JsonProperty("question")
	private String question;
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
	public String getRelatedLectureIds() {
		return relatedLectureIds;
	}

	@JsonProperty("relatedLectureIds")
	public void setRelatedLectureIds(String relatedLectureIds) {
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

}
