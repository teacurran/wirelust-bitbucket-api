package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 12-Oct-2015
 *
 * @author T. Curran
 */
public class PullRequestActivityList extends PageableList implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	String next;

	ArrayList<PullRequestActivity> values;

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public List<PullRequestActivity> getValues() {
		return values;
	}

	public void setValues(List<PullRequestActivity> values) {
		this.values = new ArrayList<>(values);
	}
}
