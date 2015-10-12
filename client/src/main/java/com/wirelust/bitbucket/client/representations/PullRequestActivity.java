package com.wirelust.bitbucket.client.representations;

/**
 * Date: 12-Oct-2015
 *
 * @author T. Curran
 */
public class PullRequestActivity {
	PullRequest pullRequest;
	Comment comment;
	Update update;

	public PullRequest getPullRequest() {
		return pullRequest;
	}

	public void setPullRequest(PullRequest pullRequest) {
		this.pullRequest = pullRequest;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}
}
