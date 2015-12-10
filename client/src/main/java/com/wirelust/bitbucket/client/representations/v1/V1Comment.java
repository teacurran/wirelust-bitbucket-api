package com.wirelust.bitbucket.client.representations.v1;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wirelust.bitbucket.client.Constants;

/**
 * Date: 04-Dec-2015
 *
 * @author T. Curran
 */
public class V1Comment {

	String username;
	Long pullRequestId;
	Long commentId;
	String displayName;
	Long parentId;
	Boolean deleted;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT_V1)
	Date utcCreatedOn;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT_V1)
	Date utcLastUpdated;

	String filenameHash;
	String baseRev;
	String filename;
	String content;
	String contentRendered;
	String userAvatarUrl;
	Integer lineFrom;
	Integer lineTo;
	String destRev;
	String anchor;
	Boolean isSpam;
	V1Repo prRepo;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getPullRequestId() {
		return pullRequestId;
	}

	public void setPullRequestId(Long pullRequestId) {
		this.pullRequestId = pullRequestId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getUtcCreatedOn() {
		return utcCreatedOn;
	}

	public void setUtcCreatedOn(Date utcCreatedOn) {
		this.utcCreatedOn = utcCreatedOn;
	}

	public Date getUtcLastUpdated() {
		return utcLastUpdated;
	}

	public void setUtcLastUpdated(Date utcLastUpdated) {
		this.utcLastUpdated = utcLastUpdated;
	}

	public String getFilenameHash() {
		return filenameHash;
	}

	public void setFilenameHash(String filenameHash) {
		this.filenameHash = filenameHash;
	}

	public String getBaseRev() {
		return baseRev;
	}

	public void setBaseRev(String baseRev) {
		this.baseRev = baseRev;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentRendered() {
		return contentRendered;
	}

	public void setContentRendered(String contentRendered) {
		this.contentRendered = contentRendered;
	}

	public String getUserAvatarUrl() {
		return userAvatarUrl;
	}

	public void setUserAvatarUrl(String userAvatarUrl) {
		this.userAvatarUrl = userAvatarUrl;
	}

	public Integer getLineFrom() {
		return lineFrom;
	}

	public void setLineFrom(Integer lineFrom) {
		this.lineFrom = lineFrom;
	}

	public Integer getLineTo() {
		return lineTo;
	}

	public void setLineTo(Integer lineTo) {
		this.lineTo = lineTo;
	}

	public String getDestRev() {
		return destRev;
	}

	public void setDestRev(String destRev) {
		this.destRev = destRev;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public Boolean getIsSpam() {
		return isSpam;
	}

	public void setIsSpam(Boolean spam) {
		isSpam = spam;
	}

	public Boolean getSpam() {
		return isSpam;
	}

	public void setSpam(Boolean spam) {
		isSpam = spam;
	}

	public V1Repo getPrRepo() {
		return prRepo;
	}

	public void setPrRepo(V1Repo prRepo) {
		this.prRepo = prRepo;
	}
}
