package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 08-Oct-2015
 *
 * @author T. Curran
 */
public class PullRequestList extends PageableList implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	String next;

	ArrayList<PullRequest> values;

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public List<PullRequest> getValues() {
		return values;
	}

	public void setValues(List<PullRequest> values) {
		this.values = new ArrayList<>(values);
	}
}
