package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wirelust.bitbucket.client.Constants;

/**
 * Date: 12-Oct-2015
 *
 * @author T. Curran
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	Long id;
	Comment parent;
	HashMap<String, List<Link>> links;
	Content content;
	User user;
	CommentLine inline;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
	Date createdOn;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
	Date updatedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Comment getParent() {
		return parent;
	}

	public void setParent(Comment parent) {
		this.parent = parent;
	}

	public Map<String, List<Link>> getLinks() {
		return links;
	}

	public void setLinks(Map<String, List<Link>> links) {
		this.links = new HashMap<>(links);
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CommentLine getInline() {
		return inline;
	}

	public void setInline(CommentLine inline) {
		this.inline = inline;
	}

}
