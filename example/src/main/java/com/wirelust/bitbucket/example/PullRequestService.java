package com.wirelust.bitbucket.example;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.wirelust.bitbucket.client.BitBucketUndocumentedClient;
import com.wirelust.bitbucket.client.BitbucketV2Client;
import com.wirelust.bitbucket.client.representations.PullRequest;
import com.wirelust.bitbucket.client.representations.PullRequestList;
import com.wirelust.bitbucket.client.representations.TaskList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 15-Oct-2015
 *
 * @author T. Curran
 */
@Named
@ViewScoped
public class PullRequestService implements Serializable {

	private static final long serialVersionUID = 5934821136854974901L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PullRequestService.class);

	@Inject
	BitbucketV2Client bitbucketV2Client;

	@Inject
	BitBucketUndocumentedClient bitBucketUndocumentedClient;

	@Inject
	RepositoryService repositoryService;

	String repositoryOwner;
	String repositoryName;
	PullRequestList pullRequestList;
	String status;
	Long id;
	PullRequest pullRequest;
	TaskList taskList;
	String diff;

	public TaskList getTaskList() {
		if (taskList == null && repositoryService.getRepository() != null && id != null) {
			Response response = null;
			try {
				response = bitBucketUndocumentedClient.getPullRequestTasks(repositoryOwner, repositoryName, id);
				if (response.getStatus() == HttpServletResponse.SC_OK) {
					taskList = response.readEntity(TaskList.class);
				} else {
					LOGGER.error("error getting task list. response:{}", response.getStatus());
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
		}
		return taskList;
	}

	public PullRequest getPullRequest() {
		if (pullRequest == null && repositoryService.getRepository() != null) {
			Response response = null;
			try {
				response = bitbucketV2Client.getPullRequestById(repositoryOwner, repositoryName, id);
				if (response.getStatus() == HttpServletResponse.SC_OK) {
					pullRequest = response.readEntity(PullRequest.class);
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
		}
		return pullRequest;
	}

	public String getDiff() {
		if (diff == null && repositoryService.getRepository() != null) {
			Response response = null;
			try {
				response = bitbucketV2Client.getPullRequestDiff(repositoryOwner, repositoryName, id);
				if (response.getStatus() == HttpServletResponse.SC_OK) {
					diff = response.readEntity(String.class);
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
		}
		return diff;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		this.pullRequest = null;
		this.taskList = null;
		this.diff = null;
	}
}
