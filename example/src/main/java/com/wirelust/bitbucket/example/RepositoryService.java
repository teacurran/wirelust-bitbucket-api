package com.wirelust.bitbucket.example;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.wirelust.bitbucket.client.BitbucketV2Client;
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

		if (repositoryList != null) {
			return repositoryList.getValues();
		}
		return null;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
