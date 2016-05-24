package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 07-Oct-2015
 *
 * @author T. Curran
 */
public class RepositoryList extends PageableList implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	List<Repository> values;

	public List<Repository> getValues() {
		return values;
	}

	public void setValues(List<Repository> values) {
		this.values = values;
	}
}
