package com.wirelust.bitbucket.client;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Date: 04-Dec-2015
 *
 * @author T. Curran
 */
public interface BitbucketV1Client {

	/**
	 * /1.0/repositories/{accountname}/{repo_slug}/pullrequests/{pull_request_id}/comments
	 */
	@POST
	@Path("/1.0/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postPullRequestComment(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("pull_request_id") Long pullRequestId,
		@FormParam("content") String content);

	@DELETE
	@Path("/1.0/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/comments")
	public Response deletePullRequestComment(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("pull_request_id") Long pullRequestId);

}
