package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;

/**
 * Date: 10-Oct-2015
 *
 * @author T. Curran
 */
public class Participant implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	String role;
	User user;
	Boolean approved;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean isApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
}
