package com.wirelust.bitbucket.client.representations;

/**
 * Date: 14-Oct-2015
 *
 * @author T. Curran
 */
public class CommentLine {
	Integer to;
	Integer from;
	String path;

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
