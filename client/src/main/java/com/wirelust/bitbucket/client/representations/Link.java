package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;

/**
 * Date: 07-Oct-2015
 *
 * @author T. Curran
 */
public class Link implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	String href;
	String name;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
