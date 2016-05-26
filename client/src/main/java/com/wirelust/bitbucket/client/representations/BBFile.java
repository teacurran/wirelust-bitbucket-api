package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 08-Nov-2015
 *
 * @author T. Curran
 */
public class BBFile implements Serializable {

	private static final long serialVersionUID = 7387646855881904997L;

	private HashMap<String, List<Link>> links;

	public Map<String, List<Link>> getLinks() {
		return links;
	}

	public void setLinks(HashMap<String, List<Link>> links) {
		this.links = links;
	}
}
