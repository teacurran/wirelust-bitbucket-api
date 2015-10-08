package com.wirelust.bitbucket.client.representations;

import java.util.List;

/**
 * Date: 08-Oct-2015
 *
 * @author T. Curran
 */
public class PullRequestList {

	Integer page;
	Integer pagelen;
	Integer size;
	String next;

	List<PullRequest> values;

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

	public List<PullRequest> getValues() {
		return values;
	}

	public void setValues(List<PullRequest> values) {
		this.values = values;
	}
}
