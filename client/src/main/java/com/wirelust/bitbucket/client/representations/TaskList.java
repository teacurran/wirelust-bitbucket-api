package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 27-Nov-2016
 *
 * @author T. Curran
 */
public class TaskList extends PageableList implements Serializable {

	private static final long serialVersionUID = 2541689069225147661L;

	String next;

	private ArrayList<Task> values;

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public List<Task> getValues() {
		return values;
	}

	public void setValues(List<Task> values) {
		this.values = new ArrayList<>(values);
	}

}
