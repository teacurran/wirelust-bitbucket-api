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
import com.wirelust.bitbucket.client.representations.Repository;
import com.wirelust.bitbucket.client.representations.RepositoryList;

/**
 * Date: 15-Oct-2015
 *
 * @author T. Curran
 */
@Named
@ViewScoped
public class RepositoryService implements Serializable {

	private static final long serialVersionUID = 5934821136854974901L;

	@Inject
	BitbucketV2Client bitbucketV2Client;

	RepositoryList repositoryList;
	String user;
	Repository repository;
	String repositoryOwner;
	String repositoryName;
	CommitList commitList;

	public List<Repository> getList() {
		Response response;
		if (user != null) {
			response = bitbucketV2Client.getRepositoriesByOwner(user);
		} else {
			response = bitbucketV2Client.getAllRepositories();
		}

		if (response.getStatus() == HttpServletResponse.SC_OK) {
			repositoryList = response.readEntity(RepositoryList.class);
		}

		response.close();

		if (repositoryList != null) {
			return repositoryList.getValues();
		}
		return new ArrayList<>();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Repository getRepository() {
		if (repositoryName != null && repository == null) {
			Response response = bitbucketV2Client.getRepositoryByOwnerRepo(repositoryOwner, repositoryName);
			if (response.getStatus() == HttpServletResponse.SC_OK) {
				repository = response.readEntity(Repository.class);
			}
			response.close();
		}
		return repository;
	}

	public CommitList getRepositoryCommits() {
		if (commitList == null && getRepository() != null) {
			Response response = bitbucketV2Client.getCommitsByOwnerRepo(repositoryOwner, repositoryName);
			if (response.getStatus() == HttpServletResponse.SC_OK) {
				commitList = response.readEntity(CommitList.class);
			}
			response.close();
		}
		return commitList;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
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
}
