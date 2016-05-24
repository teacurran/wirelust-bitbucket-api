package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 10-Oct-2015
 *
 * @author T. Curran
 */
public class CommentList extends PageableList implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	String next;

	private List<Comment> values;

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public List<Comment> getValues() {
		return values;
	}

	public void setValues(List<Comment> values) {
		this.values = values;
	}
}
