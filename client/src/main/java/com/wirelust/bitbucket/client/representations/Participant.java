package com.wirelust.bitbucket.client.representations;

/**
 * Date: 10-Oct-2015
 *
 * @author T. Curran
 */
public class Participant {

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

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
}
