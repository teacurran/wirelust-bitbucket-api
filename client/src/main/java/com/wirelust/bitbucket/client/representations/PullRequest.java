package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wirelust.bitbucket.client.Constants;

/**
 * Date: 08-Oct-2015
 *
 * @author T. Curran
 */
public class PullRequest implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	public enum State {
		OPEN, DECLINED, MERGED;

		@JsonCreator
		public static State fromString(String key) {
			return key == null ? null : State.valueOf(key.toUpperCase());
		}
	}


	Long id;
	String title;
	String description;
	String reason;
	User author;
	User closedBy;
	State state;
	private HashMap<String, List<Link>> links;
	Boolean closeSourceBranch;
	CommitSource source;
	CommitSource destination;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
	Date createdOn;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
	Date updatedOn;
	Commit mergeCommit;
	private ArrayList<User> reviewers;
	private ArrayList<Participant> participants;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(User closedBy) {
		this.closedBy = closedBy;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Map<String, List<Link>> getLinks() {
		return links;
	}

	public void setLinks(Map<String, List<Link>> links) {
		this.links = new HashMap<>(links);
	}

	public Boolean getCloseSourceBranch() {
		return closeSourceBranch;
	}

	public void setCloseSourceBranch(Boolean closeSourceBranch) {
		this.closeSourceBranch = closeSourceBranch;
	}

	public CommitSource getSource() {
		return source;
	}

	public void setSource(CommitSource source) {
		this.source = source;
	}

	public CommitSource getDestination() {
		return destination;
	}

	public void setDestination(CommitSource destination) {
		this.destination = destination;
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

	public Commit getMergeCommit() {
		return mergeCommit;
	}

	public void setMergeCommit(Commit mergeCommit) {
		this.mergeCommit = mergeCommit;
	}

	public List<User> getReviewers() {
		return reviewers;
	}

	public void setReviewers(List<User> reviewers) {
		this.reviewers = new ArrayList<>(reviewers);
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = new ArrayList<>(participants);
	}
}
