package com.wirelust.bitbucket.client;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Date: 26-Sep-2015
 *
 * @author T. Curran
 */
public interface BitbucketV2Client {

	/**
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}
	 */
	@GET
	@Path("/2.0/repositories/{owner}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRepositoriesByOwner(
			@PathParam("owner") String owner);

	/**
	 * GET https://api.bitbucket.org/2.0/repositories/{teamname}
	 */
	@GET
	@Path("/2.0/repositories/{teamname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRepositoriesByTeam(
			@PathParam("teamname") String teamName);

	/**
	 * GET https://api.bitbucket.org/2.0/repositories
	 */
	@GET
	@Path("/2.0/repositories")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRepositories();

	/**
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRepositoryByOwnerRepo(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug);

	/**
	 * GET https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/commits
	 * @return com.wirelust.bitbucket.client.representations.CommitList
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/commits")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommitsByOwnerRepo(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug);

	/**
	 * GET https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/diff/{revision}
	 * NOTE: NOT in the official Bitbucket documentation
	 * @return java.lang.String
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/diff/{revision}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiffByOwnerRepoRevision(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("revision") String revision);

	/**
	 * GET https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/commit/{revision}
	 * @return com.wirelust.bitbucket.client.representations.Commit
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/commit/{revision}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommitByOwnerRepoRevision(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("revision") String revision);

	/**
	 * GET https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/comments
	 * @return com.wirelust.bitbucket.client.representations.CommentList
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentsByOwnerRepoRevision(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("revision") String revision);

	/**
	 * GET https://bitbucket.org/api/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/comments/{comment_id}
	 * @return com.wirelust.bitbucket.client.representations.Comment
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
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests?state=[OPEN, MERGED, DECLINED]
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
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}
	 * @return com.wirelust.bitbucket.client.representations.PullRequest
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestById(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") String id);

	/**
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/activity
	 * @return com.wirelust.bitbucket.client.representations.PullRequestActivity
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/activity")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestActivityById(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") String id);

	/**
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/commits
	 * @return com.wirelust.bitbucket.client.representations.CommitList
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/commits")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestCommits(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") String id);

	/**
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/commits
	 * @return com.wirelust.bitbucket.client.representations.CommentList
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
	 * @return com.wirelust.bitbucket.client.representations.String
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/diff")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getPullRequestDiff(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") String id);

	/**
	 * GET https://api.bitbucket.org/2.0/teams/?role={role}
	 */
	@GET
	@Path("/2.0/teams")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTeams(
		@QueryParam("role") String role
	);

	/**
	 * GET https://api.bitbucket.org/2.0/teams/{teamname}
	 */
	@GET
	@Path("/2.0/teams/{teamname}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTeamByName(
		@PathParam("teamname") String teamName
	);

	/**
	 * GET https://api.bitbucket.org/2.0/teams/{teamname}/members
	 */
	@GET
	@Path("/2.0/teams/{teamname}/members")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTeamMembers(
		@PathParam("teamname") String teamName
	);

	/**
	 * GET https://api.bitbucket.org/2.0/teams/{teamname}/followers
	 */
	@GET
	@Path("/2.0/teams/{teamname}/followers")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTeamFollowers(
		@PathParam("teamname") String teamName
	);

	/**
	 * GET https://api.bitbucket.org/2.0/teams/{teamname}/following
	 */
	@GET
	@Path("/2.0/teams/{teamname}/following")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTeamFollowing(
		@PathParam("teamname") String teamName
	);

	/**
	 * GET https://bitbucket.org/api/2.0/user
	 */
	@GET
	@Path("/2.0/user")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getUser();

	/**
	 * GET https://bitbucket.org/api/2.0/users/{username}
	 */
	@GET
	@Path("/2.0/users/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getUser(
			@PathParam("username") String username);

	/**
	 * GET https://bitbucket.org/api/2.0/users/{username}/followers
	 */
	@GET
	@Path("/2.0/users/{username}/followers")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getUserFollowers(
			@PathParam("username") String username);
}
