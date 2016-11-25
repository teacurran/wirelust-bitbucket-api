package com.wirelust.bitbucket.client.representations;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Date: 25-Nov-2016
 *
 * @author T. Curran
 */
public class Task extends Comment {

	public enum State {
		UNRESOLVED, RESOLVED;

		@JsonCreator
		public static PullRequest.State fromString(String key) {
			return key == null ? null : PullRequest.State.valueOf(key.toUpperCase());
		}
	}

	User creator;
	State state;

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
