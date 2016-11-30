package com.wirelust.bitbucket.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wirelust.bitbucket.client.representations.Task;

/**
 * Date: 29-Nov-2016
 *
 * @author T. Curran
 *
 * Methods in this client interface are not released in the public BitBUcket API.
 * The endpoints can be accessed at: https://bitbucket.org/!api/
 *
 * Auth tokens obtained from the normal v1 and v2 APIs appear to work but your mileage may vary.
 */
public interface BitBucketUndocumentedClient {

	@GET
	@Path("/internal/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPullRequestTasks(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("pull_request_id") Long pullRequestId);

	@POST
	@Path("/internal/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postPullRequestTask(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("pull_request_id") Long pullRequestId,
		Task task);

	@PUT
	@Path("/internal/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/tasks/{task_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putPullRequestTask(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("pull_request_id") Long pullRequestId,
		@PathParam("task_id") Long taskId,
		Task task);

}
