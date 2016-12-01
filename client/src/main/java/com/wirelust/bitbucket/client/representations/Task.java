package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wirelust.bitbucket.client.Constants;

/**
 * Date: 25-Nov-2016
 *
 * @author T. Curran
 */
public class Task implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	public enum State {
		UNRESOLVED, RESOLVED;

		@JsonCreator
		public static PullRequest.State fromString(String key) {
			return key == null ? null : PullRequest.State.valueOf(key.toUpperCase());
		}
	}

	Long id;
	User creator;
	State state;
	Comment comment;
	Map<String, List<Link>> links;
	Content content;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
	Date createdOn;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
	Date updatedOn;

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Map<String, List<Link>> getLinks() {
		return links;
	}

	public void setLinks(Map<String, List<Link>> links) {
		this.links = links;
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
}
