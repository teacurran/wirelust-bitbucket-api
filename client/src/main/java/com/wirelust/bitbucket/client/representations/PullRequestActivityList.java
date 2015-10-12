package com.wirelust.bitbucket.client.representations;

import java.util.List;

/**
 * Date: 12-Oct-2015
 *
 * @author T. Curran
 */
public class PullRequestActivityList {

	Integer page;
	Integer pagelen;
	Integer size;
	String next;

	List<PullRequestActivity> values;

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
		this.values = values;
	}
}
