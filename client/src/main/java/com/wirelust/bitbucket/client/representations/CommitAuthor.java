package com.wirelust.bitbucket.client.representations;

/**
 * Date: 10-Oct-2015
 *
 * @author T. Curran
 */
public class CommitAuthor {
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
