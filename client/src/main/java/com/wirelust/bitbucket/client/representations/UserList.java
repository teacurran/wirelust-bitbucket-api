package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 23-Oct-2015
 *
 * @author T. Curran
 */
public class UserList extends PageableList implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	ArrayList<User> values;

	public List<User> getValues() {
		return values;
	}

	public void setValues(List<User> values) {
		this.values = new ArrayList<>(values);
	}
}
