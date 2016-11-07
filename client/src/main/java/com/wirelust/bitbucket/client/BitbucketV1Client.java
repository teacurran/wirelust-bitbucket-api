package com.wirelust.bitbucket.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wirelust.bitbucket.client.representations.v1.Privilege;
import com.wirelust.bitbucket.client.representations.v1.V1Comment;

/**
 * Date: 04-Dec-2015
 *
 * @author T. Curran
 */
public interface BitbucketV1Client {


	/**
	 * /1.0/privileges/{accountname}/?filter=admin
	 */
	@GET
	@Path("/1.0/privileges/{owner}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPrivileges(
		@PathParam("owner") String owner,
		@QueryParam("filter") Privilege.Type filter);

	/**
	 * /1.0/privileges/{accountname}/?filter=admin
	 */
	@GET
	@Path("/1.0/privileges/{owner}/{repo_slug}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPrivileges(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String reopSlug,
		@QueryParam("filter") Privilege.Type filter);

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

	@POST
	@Path("/1.0/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postPullRequestComment(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("pull_request_id") Long pullRequestId,
		V1Comment comment);

	@PUT
	@Path("/1.0/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/comments/{comment_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putPullRequestComment(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("pull_request_id") Long pullRequestId,
		@PathParam("comment_id") Long commentId,
		V1Comment comment);

	@DELETE
	@Path("/1.0/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/comments/{comment_id}")
	public Response deletePullRequestComment(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("pull_request_id") Long pullRequestId,
		@PathParam("comment_id") Long commentId
	);

}
