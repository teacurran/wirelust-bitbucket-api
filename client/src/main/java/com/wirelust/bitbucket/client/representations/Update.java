package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wirelust.bitbucket.client.Constants;

/**
 * Date: 12-Oct-2015
 *
 * @author T. Curran
 */
public class Update implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	public enum State {
		OPEN, DECLINED, MERGED;

		@JsonCreator
		public static State fromString(String key) {
			return key == null ? null : State.valueOf(key.toUpperCase());
		}
	}

	String description;
	String title;
	CommitSource destination;
	CommitSource source;
	String reason;
	State state;
	User author;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
	Date date;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CommitSource getDestination() {
		return destination;
	}

	public void setDestination(CommitSource destination) {
		this.destination = destination;
	}

	public CommitSource getSource() {
		return source;
	}

	public void setSource(CommitSource source) {
		this.source = source;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
