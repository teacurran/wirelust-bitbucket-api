package com.wirelust.bitbucket.client.representations;

/**
 * Date: 23-May-2016
 *
 * @author T. Curran
 */
public class PageableList {

	Integer page;
	Integer pagelen;
	Integer size;

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
}
