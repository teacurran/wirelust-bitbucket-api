package com.wirelust.bitbucket.client.representations;

import java.util.List;

/**
 * Date: 07-Oct-2015
 *
 * @author T. Curran
 */
public class RepositoryList {

	Integer page;
	Integer pagelen;
	Integer size;

	List<Repository> values;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPagelen() {
		return pagelen;
	}

	public void setPagelen(Integer pagelen) {
		this.pagelen = pagelen;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<Repository> getValues() {
		return values;
	}

	public void setValues(List<Repository> values) {
		this.values = values;
	}
}
