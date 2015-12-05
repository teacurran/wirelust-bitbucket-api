package com.wirelust.bitbucket.client.representations.v1;

/**
 * Date: 04-Dec-2015
 *
 * @author T. Curran
 */
public class V1Repo {
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
