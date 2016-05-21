package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 23-Oct-2015
 *
 * @author T. Curran
 */
public class UserList implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	Integer page;
	Integer pagelen;
	Integer size;

	private List<User> values;

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

	public List<User> getValues() {
		return values;
	}

	public void setValues(List<User> values) {
		this.values = values;
	}
}
