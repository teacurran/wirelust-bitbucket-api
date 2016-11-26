package com.wirelust.bitbucket.client;

import com.wirelust.bitbucket.client.representations.BuildStatus;
import com.wirelust.bitbucket.client.representations.Task;
import com.wirelust.bitbucket.client.representations.v1.V1Comment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Date: 26-Sep-2015
 *
 * @author T. Curran
 */
public interface BitbucketV2Client extends BitbucketV1Client {

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
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/statuses/build/{key}
	 * @return com.wirelust.bitbucket.client.representations.BuildStatus
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/statuses/build/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBuildStatus(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("revision") String revision,
		@PathParam("key") String key);

	/**
	 * POST https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/statuses/build
	 * @return com.wirelust.bitbucket.client.representations.BuildStatus
	 */
	@POST
	@Path("/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/statuses/build")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postBuildStatus(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("revision") String revision,
		BuildStatus buildStatus);

	/**
	 * PUT https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/statuses/build/{key}
	 * @return com.wirelust.bitbucket.client.representations.BuildStatus
	 */
	@PUT
	@Path("/2.0/repositories/{owner}/{repo_slug}/commit/{revision}/statuses/build/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putBuildStatus(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("revision") String revision,
		@PathParam("key") String key,
		BuildStatus buildStatus);

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
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{1}
	 * @return com.wirelust.bitbucket.client.representations.PullRequest
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestById(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") Long id);

	/**
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{1}/activity
	 * @return com.wirelust.bitbucket.client.representations.PullRequestActivity
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/activity")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestActivityById(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") Long id);

	/**
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{1}/commits
	 * @return com.wirelust.bitbucket.client.representations.CommitList
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/commits")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestCommits(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") Long id);

	/**
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{1}/commits
	 * @return com.wirelust.bitbucket.client.representations.CommentList
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestComments(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") Long id);

	/**
	 * GET https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{1}/commits
	 * @return com.wirelust.bitbucket.client.representations.CommentList
	 */
	@GET
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPullRequestCommentsWithPage(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") Long id,
			@QueryParam("page") Integer page);

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
			@PathParam("id") Long id);

	/**
	 * POST https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/approve
	 */
	@POST
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/approve")
	@Produces(MediaType.TEXT_PLAIN)
	public Response postPullRequestApproval(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") Long id);

	/**
	 * DELETE https://api.bitbucket.org/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/approve
	 */
	@DELETE
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{id}/approve")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deletePullRequestApproval(
			@PathParam("owner") String owner,
			@PathParam("repo_slug") String repoSlug,
			@PathParam("id") Long id);

	@POST
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postPullRequestTask(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("pull_request_id") Long pullRequestId,
		Task task);

	@PUT
	@Path("/2.0/repositories/{owner}/{repo_slug}/pullrequests/{pull_request_id}/tasks/{task_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putPullRequestTask(
		@PathParam("owner") String owner,
		@PathParam("repo_slug") String repoSlug,
		@PathParam("pull_request_id") Long pullRequestId,
		@PathParam("task_id") Long taskId,
		Task task);

	/**
	 * GET https://api.bitbucket.org/2.0/snippets/{username}
	*/
	@GET
	@Path("/2.0/snippets/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSnippetsByUsername(
		@PathParam("username") String username
	);

	/**
	 * GET https://api.bitbucket.org/2.0/snippets
	 */
	@GET
	@Path("/2.0/snippets")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSnippets(
		@QueryParam("role") String role
	);

	/**
	 * GET https://api.bitbucket.org/2.0/snippets/{username}/{id}
	 */
	@GET
	@Path("/2.0/snippets/{username}/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSnippetByUsernameId(
		@PathParam("username") String username,
		@PathParam("id") String id
	);

	/**
	 * GET https://api.bitbucket.org/2.0/snippets/{username or teamname}/{snippets 1}/comments
	 */
	@GET
	@Path("/2.0/snippets/{username}/{snippit_id}/comments")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSnippitComments(
		@PathParam("username") String username,
		@PathParam("snippit_id") String snippitId
	);

	/**
	 * GET https://api.bitbucket.org/2.0/snippets/{username or teamname}/{snippets 1}/comments/{comment 1}
	 */
	@GET
	@Path("/2.0/snippets/{username}/{snippit_id}/comments/{comment_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSnippitComment(
		@PathParam("username") String username,
		@PathParam("snippit_id") String snippitId,
		@PathParam("comment_id") String commentId
	);

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
	 * GET https://api.bitbucket.org/2.0/teams/{teamname}/repositories
	 */
	@GET
	@Path("/2.0/teams/{teamname}/repositories")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTeamRepositories(
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
