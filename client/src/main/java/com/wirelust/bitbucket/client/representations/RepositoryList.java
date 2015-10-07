package com.wirelust.bitbucket.client.representations;

import java.util.List;

/**
 * Date: 07-Oct-2015
 *
 * @author T. Curran
 */
public class RepositoryList {
	Integer pagelen;
	List<Repository> values;

	public Integer getPagelen() {
		return pagelen;
	}

	public void setPagelen(Integer pagelen) {
		this.pagelen = pagelen;
	}

	public List<Repository> getValues() {
		return values;
	}

	public void setValues(List<Repository> values) {
		this.values = values;
	}
}
