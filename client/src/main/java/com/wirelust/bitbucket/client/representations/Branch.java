package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;

/**
 * Date: 08-Oct-2015
 *
 * @author T. Curran
 */
public class Branch implements Serializable {

	private static final long serialVersionUID = 7387646855881904997L;

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
