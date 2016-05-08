package com.wirelust.bitbucket.client.representations;

/**
 * Date: 08-May-2016
 *
 * @author T. Curran
 *
 * This class exists because posting build status with links will not work
 */
public class BuildStatusPost {

	BuildStatus.STATE state;
	String type;
	String key;
	String name;
	String url;
	String description;

	public BuildStatus.STATE getState() {
		return state;
	}

	public void setState(BuildStatus.STATE state) {
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

}
