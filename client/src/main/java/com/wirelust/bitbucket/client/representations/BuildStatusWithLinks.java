package com.wirelust.bitbucket.client.representations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 19-May-2016
 *
 * @author T. Curran
 *
 * This class exists because posting build status with links will not work
 *
 */
public class BuildStatusWithLinks extends BuildStatus {

	HashMap<String, List<Link>> links;

	public Map<String, List<Link>> getLinks() {
		return links;
	}

	public void setLinks(Map<String, List<Link>> links) {
		this.links = new HashMap<>(links);
	}
}
