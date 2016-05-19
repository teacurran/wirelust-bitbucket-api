package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;

/**
 * Date: 10-Oct-2015
 *
 * @author T. Curran
 */
public class CommitAuthor implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	String raw;
	User user;

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
