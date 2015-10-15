package com.wirelust.bitbucket.example;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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

	public List<Repository> getList() {
		Response response = bitbucketV2Client.getAllRepositories();
		repositoryList = response.readEntity(RepositoryList.class);

		return repositoryList.getValues();
	}

}
