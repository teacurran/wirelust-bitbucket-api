package com.wirelust.bitbucket.client.representations;

/**
 * Date: 12-Oct-2015
 *
 * @author T. Curran
 */
public class Content {
	String raw;
	String markup;
	String html;

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String getMarkup() {
		return markup;
	}

	public void setMarkup(String markup) {
		this.markup = markup;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
}
