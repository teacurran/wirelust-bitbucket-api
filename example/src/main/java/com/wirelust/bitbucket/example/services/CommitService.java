package com.wirelust.bitbucket.example.services;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.wirelust.bitbucket.client.BitbucketV2Client;
import com.wirelust.bitbucket.client.representations.Commit;

/**
 * Date: 19-Oct-2015
 *
 * @author T. Curran
 */
@Named
@ViewScoped
public class CommitService implements Serializable {

	private static final long serialVersionUID = 8196457178280929905L;

	@Inject
	BitbucketV2Client bitbucketV2Client;

	String paramOwner;
	String paramRepoSlug;
	String paramRevision;

	Commit commit;
	String diff;

	public Commit getCommit() {
		if (commit == null && paramOwner != null && paramRepoSlug != null && paramRevision != null) {
			Response response = bitbucketV2Client.getCommitByOwnerRepoRevision(paramOwner, paramRepoSlug,
				paramRevision);

			if (response.getStatus() == HttpServletResponse.SC_OK) {
				commit = response.readEntity(Commit.class);
			}
		}
		return commit;
	}

	public String getDiff() {
		if (diff == null && paramOwner != null && paramRepoSlug != null && paramRevision != null) {
			Response response = bitbucketV2Client.getPullRequestDiff(paramOwner, paramRepoSlug, paramRevision);

			if (response.getStatus() == HttpServletResponse.SC_OK) {
				diff = response.readEntity(String.class);
			}
		}
		return diff;
	}

	public String getParamOwner() {
		return paramOwner;
	}

	public void setParamOwner(String paramOwner) {
		this.paramOwner = paramOwner;
	}

	public String getParamRepoSlug() {
		return paramRepoSlug;
	}

	public void setParamRepoSlug(String paramRepoSlug) {
		this.paramRepoSlug = paramRepoSlug;
	}

	public String getParamRevision() {
		return paramRevision;
	}

	public void setParamRevision(String paramRevision) {
		this.paramRevision = paramRevision;
	}
}
