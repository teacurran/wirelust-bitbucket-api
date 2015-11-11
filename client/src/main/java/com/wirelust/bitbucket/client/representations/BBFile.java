package com.wirelust.bitbucket.client.representations;

import java.util.List;
import java.util.Map;

/**
 * Date: 08-Nov-2015
 *
 * @author T. Curran
 */
public class BBFile {

	Map<String, List<Link>> links;

	public Map<String, List<Link>> getLinks() {
		return links;
	}

	public void setLinks(Map<String, List<Link>> links) {
		this.links = links;
	}
}
