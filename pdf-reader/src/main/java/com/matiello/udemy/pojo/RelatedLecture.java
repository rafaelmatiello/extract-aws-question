package com.matiello.udemy.pojo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "_class", "id", "title", "created", "asset", "object_index", "url" })
public class RelatedLecture {

	@JsonProperty("_class")
	private String _class;
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("created")
	private String created;
	@JsonProperty("asset")
	private Asset asset;
	@JsonProperty("object_index")
	private Integer objectIndex;
	@JsonProperty("url")
	private String url;
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

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("created")
	public String getCreated() {
		return created;
	}

	@JsonProperty("created")
	public void setCreated(String created) {
		this.created = created;
	}

	@JsonProperty("asset")
	public Asset getAsset() {
		return asset;
	}

	@JsonProperty("asset")
	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	@JsonProperty("object_index")
	public Integer getObjectIndex() {
		return objectIndex;
	}

	@JsonProperty("object_index")
	public void setObjectIndex(Integer objectIndex) {
		this.objectIndex = objectIndex;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
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
