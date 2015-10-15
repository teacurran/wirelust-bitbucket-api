package com.wirelust.bitbucket.client;


/**
 * Date: 26-Sep-2015
 *
 * @author T. Curran
 */
public interface BitbucketV2Client {

	@GET
	@Path("/2.0/repositories/{owner}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRepositoriesByOwner(
			@PathParam("owner") String owner);

	@GET
	@Path("/2.0/repositories/{teamname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRepositoriesByTeam(
			@PathParam("teamname") String teamName);

	@GET
	@Path("/2.0/repositories")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRepositories();

	/**
	 * 	https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRepositoryByOwnerRepo(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug);

	/**
	 * https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/commits
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/commits")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommitsByOwnerRepo(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug);

	/**
	 * https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/commit/{revision}
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/commit/{revision}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommitByOwnerRepoRevision(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("revision") String revision);

	/**
	 * https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/comments
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentsByOwnerRepoRevision(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("revision") String revision);

	/**
	 * https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/comments/{comment_id}
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/comments/{comment_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentByOwnerRepoRevisionId(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("revision") String revision,
		@PathParam("comment_id") String commentId);

	/**
	 * https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests?state=[OPEN, MERGED, DECLINED]
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequests(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@QueryParam("state") String state);

	/**
	 * GET https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/pullrequests/activity
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/activity")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getPullRequestActivity(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug);

	/**
	 * https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestById(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") String id);

	/**
	 * https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/activity
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/activity")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestActivityById(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") String id);

	/**
	 * https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/commits
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/commits")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestCommits(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") String id);

	/**
	 * https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/commits
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestComments(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") String id);

	/**
	 * GET https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/diff
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/diff")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getPullRequestDiff(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") String id);


}
