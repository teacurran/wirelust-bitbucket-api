package com.wirelust.bitbucket.client.representations.v1;

import java.io.Serializable;

/**
 * Date: 04-Dec-2015
 *
 * @author T. Curran
 */
public class V1Repo implements Serializable {

	private static final long serialVersionUID = 7387646855881904997L;

	String owner;
	String slug;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
}
