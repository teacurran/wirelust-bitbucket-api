package com.wirelust.bitbucket.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.wirelust.bitbucket.client.BitbucketV2Client;
import com.wirelust.bitbucket.client.representations.CommitList;
import com.wirelust.bitbucket.client.representations.PullRequest;
import com.wirelust.bitbucket.client.representations.PullRequestList;
import com.wirelust.bitbucket.client.representations.Repository;
import com.wirelust.bitbucket.client.representations.RepositoryList;

/**
 * Date: 15-Oct-2015
 *
 * @author T. Curran
 */
@Named
@ViewScoped
public class PullRequestService implements Serializable {

	private static final long serialVersionUID = 5934821136854974901L;

	@Inject
	BitbucketV2Client bitbucketV2Client;

	@Inject
	RepositoryService repositoryService;

	String repositoryOwner;
	String repositoryName;
	PullRequestList pullRequestList;
	String status;

	public PullRequestList getRepositoryPullRequests() {
		if (pullRequestList == null && repositoryService.getRepository() != null) {
			Response response = null;
			try {
				response = bitbucketV2Client.getPullRequests(repositoryOwner, repositoryName, PullRequest.State.OPEN
					.name());
				if (response.getStatus() == HttpServletResponse.SC_OK) {
					pullRequestList = response.readEntity(PullRequestList.class);
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
		}
		return pullRequestList;
	}

	public String getRepositoryOwner() {
		return repositoryOwner;
	}

	public void setRepositoryOwner(String repositoryOwner) {
		this.repositoryOwner = repositoryOwner;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public PullRequestList getPullRequestList() {
		return pullRequestList;
	}

	public void setPullRequestList(PullRequestList pullRequestList) {
		this.pullRequestList = pullRequestList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		pullRequestList = null;
		this.status = status;
	}
}
