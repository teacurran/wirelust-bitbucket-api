package com.wirelust.bitbucket.client.representations;

import java.util.List;
import java.util.Map;

/**
 * Date: 08-Oct-2015
 *
 * @author T. Curran
 */
public class Commit {

	String hash;
	Map<String, List<Link>> links;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Map<String, List<Link>> getLinks() {
		return links;
	}

	public void setLinks(Map<String, List<Link>> links) {
		this.links = links;
	}
}
