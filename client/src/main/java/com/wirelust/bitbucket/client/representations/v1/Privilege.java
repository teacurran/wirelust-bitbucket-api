package com.wirelust.bitbucket.client.representations.v1;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wirelust.bitbucket.client.representations.Repository;
import com.wirelust.bitbucket.client.representations.User;

/**
 * Date: 03-May-2016
 *
 * @author T. Curran
 */
public class Privilege implements Serializable {

	private static final long serialVersionUID = 7387646855881904997L;

	public enum Type {
		ADMIN("admin"), READ("read"), WRITE("write");

		private String value;

		private Type(String value) {
			this.value = value;
		}

		@JsonValue
		public String getValue() {
			return this.value;
		}

		@JsonCreator
		public static Type fromString(String key) {
			return key == null ? null : Type.valueOf(key.toUpperCase());
		}
	}

	String repo;
	Type privilege;
	User user;
	Repository repository;

	public String getRepo() {
		return repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

	public Type getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Type privilege) {
		this.privilege = privilege;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
