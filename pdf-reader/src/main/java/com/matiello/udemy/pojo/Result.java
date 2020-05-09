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
@JsonPropertyOrder({ "_class", "id", "assessment_type", "prompt", "correct_response", "section", "question_plain",
		"related_lectures" })
public class Result {

	@JsonProperty("_class")
	private String _class;
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("assessment_type")
	private String assessmentType;
	@JsonProperty("prompt")
	private Prompt prompt;
	@JsonProperty("correct_response")
	private List<String> correctResponse = null;
	@JsonProperty("section")
	private String section;
	@JsonProperty("question_plain")
	private String questionPlain;
	@JsonProperty("related_lectures")
	private List<RelatedLecture> relatedLectures = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("_class")
	public String getClass_() {
		return _class;
	}

	@JsonProperty("_class")
	public void setClass_(String _class) {
		this._class = _class;
	}

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("assessment_type")
	public String getAssessmentType() {
		return assessmentType;
	}

	@JsonProperty("assessment_type")
	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	@JsonProperty("prompt")
	public Prompt getPrompt() {
		return prompt;
	}

	@JsonProperty("prompt")
	public void setPrompt(Prompt prompt) {
		this.prompt = prompt;
	}

	@JsonProperty("correct_response")
	public List<String> getCorrectResponse() {
		return correctResponse;
	}

	@JsonProperty("correct_response")
	public void setCorrectResponse(List<String> correctResponse) {
		this.correctResponse = correctResponse;
	}

	@JsonProperty("section")
	public String getSection() {
		return section;
	}

	@JsonProperty("section")
	public void setSection(String section) {
		this.section = section;
	}

	@JsonProperty("question_plain")
	public String getQuestionPlain() {
		return questionPlain;
	}

	@JsonProperty("question_plain")
	public void setQuestionPlain(String questionPlain) {
		this.questionPlain = questionPlain;
	}

	@JsonProperty("related_lectures")
	public List<RelatedLecture> getRelatedLectures() {
		return relatedLectures;
	}

	@JsonProperty("related_lectures")
	public void setRelatedLectures(List<RelatedLecture> relatedLectures) {
		this.relatedLectures = relatedLectures;
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
