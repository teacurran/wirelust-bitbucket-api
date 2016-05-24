package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 15-Oct-2015
 *
 * @author T. Curran
 */
public class UserFollowerList extends PageableList implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	List<User> values;

	public List<User> getValues() {
		return values;
	}

	public void setValues(List<User> values) {
		this.values = values;
	}
}
