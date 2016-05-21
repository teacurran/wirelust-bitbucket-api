package com.wirelust.bitbucket.client.representations;

import java.util.List;
import java.util.Map;

/**
 * Date: 29-Apr-2016
 *
 * @author T. Curran
 */
public class BuildStatus {

	public enum STATE {
		INPROGRESS,
		SUCCESSFUL,
		FAILED
	};

	STATE state;
	String type;
	String key;
	String name;
	String url;
	String description;
	private Map<String, List<Link>> links;

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, List<Link>> getLinks() {
	return links;
}

	public void setLinks(Map<String, List<Link>> links) {
		this.links = links;
	}
}
