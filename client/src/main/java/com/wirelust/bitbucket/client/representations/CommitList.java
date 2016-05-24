package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 10-Oct-2015
 *
 * @author T. Curran
 */
public class CommitList extends PageableList implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	String next;

	private List<Commit> values;

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public List<Commit> getValues() {
		return values;
	}

	public void setValues(List<Commit> values) {
		this.values = values;
	}
}
