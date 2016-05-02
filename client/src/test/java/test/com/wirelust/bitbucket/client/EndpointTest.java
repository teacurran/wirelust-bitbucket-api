package test.com.wirelust.bitbucket.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.wirelust.bitbucket.client.BitbucketAuthClient;
import com.wirelust.bitbucket.client.BitbucketV1Client;
import com.wirelust.bitbucket.client.BitbucketV2Client;
import com.wirelust.bitbucket.client.Constants;
import com.wirelust.bitbucket.client.representations.*;
import com.wirelust.bitbucket.client.representations.auth.AccessToken;
import com.wirelust.bitbucket.client.representations.v1.V1Comment;
import org.apache.http.auth.AUTH;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.ConfigurableMavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.wickedsource.diffparser.api.DiffParser;
import org.wickedsource.diffparser.api.UnifiedDiffParser;
import org.wickedsource.diffparser.api.model.Diff;
import test.com.wirelust.bitbucket.client.providers.JacksonConfigurationProvider;

/**
 * Date: 07-Oct-2015
 *
 * @author T. Curran
 */
@RunWith(Arquillian.class)
public class EndpointTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(EndpointTest.class);

	static final String ROOT_URL = "http://localhost:8080/client-test/api/";
	static final String AUTH_ROOT_URL = "http://localhost:8080/client-test/site/";

	static final String DATE_FORMAT = "yyyy-MM-dd";
	static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX";

	static final String MOCK_USER_ID = "-";

	ResteasyClient client;
	ResteasyWebTarget target;
	BitbucketV1Client bitbucketV1Client;
	BitbucketV2Client bitbucketV2Client;
	BitbucketAuthClient bitbucketAuthClient;
	SimpleDateFormat simpleDateFormat;
	SimpleDateFormat simpleDateTimeFormat;
	SimpleDateFormat simpleDateTimeFormat2;

	@Deployment
	public static WebArchive create() {
		WebArchive testWar = ShrinkWrap.create(WebArchive.class, "client-test.war");
		testWar.addPackages(true, "com.wirelust.bitbucket.client");
		testWar.addPackages(true, "test.com.wirelust.bitbucket.client");
		testWar.setWebXML(new File("src/test/webapp/WEB-INF/web.xml"));

		File dir = new File("src/test/resources");
		addResourceFilesToArchive(testWar, dir);

		ConfigurableMavenResolverSystem mvnResolver = Maven.configureResolver();
		PomEquippedResolveStage pers = mvnResolver.loadPomFromFile("pom.xml");
		testWar.addAsLibraries(pers.resolve("org.apache.commons:commons-io").withTransitivity().asFile());
		testWar.addAsLibraries(pers.resolve("org.wickedsource:diffparser").withTransitivity().asFile());

		// I'm not including all of the TEST scoped dependencies any longer because jacoco will get an error on startup
		//testWar.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importDependencies(ScopeType.TEST).resolve
		//	().withTransitivity().asFile());

		System.out.println("test.war:" + testWar.toString(true));
		LOGGER.debug("test deployment: {}", testWar.toString(true));

		return testWar;
	}

	@Before
	public void init() {
		client = new ResteasyClientBuilder().build();
		client.register(JacksonConfigurationProvider.class);

		target = client.target(ROOT_URL);
		bitbucketV1Client = target.proxy(BitbucketV1Client.class);

		target = client.target(ROOT_URL);
		bitbucketV2Client = target.proxy(BitbucketV2Client.class);

		target = client.target(AUTH_ROOT_URL);
		bitbucketAuthClient = target.proxy(BitbucketAuthClient.class);

		TimeZone gmtZone = TimeZone.getTimeZone("GMT+0");

		simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		simpleDateFormat.setTimeZone(gmtZone);

		simpleDateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		simpleDateTimeFormat.setTimeZone(gmtZone);

		simpleDateTimeFormat2 = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_2);
		simpleDateTimeFormat2.setTimeZone(gmtZone);
	}

	@After
	public void destroy() {
	}

	@Test
	public void shouldBeAbleToDeseralizeAuthToken() throws Exception {

		Response response = bitbucketAuthClient.getTokenByUsernamePassword("password", "username", "password");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		AccessToken accessToken = response.readEntity(AccessToken.class);
		Assert.assertEquals("gX0XmlxGGOmJXT3dBiurs8nkfgqNXlqni-nplZbOyEDwuvIWTpldCW-FRlFcP4YWtrQJSTtBcDdt324",
			accessToken.getAccessToken());
	}

	@Test
	public void shouldBeAbleToDeseralizeGetAllRepositories() throws Exception {

		Response response = bitbucketV2Client.getAllRepositories();
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		RepositoryList repositoryList = response.readEntity(RepositoryList.class);

		Assert.assertEquals(10, (long) repositoryList.getPagelen());

		Repository repository = repositoryList.getValues().get(0);
		Assert.assertEquals("hg", repository.getScm());

		Date createdDate = simpleDateTimeFormat.parse("2008-06-25T00:53:00.273366+00:00");
		Assert.assertEquals(createdDate, repository.getCreatedOn());

		Map<String, List<Link>> links = repository.getLinks();
		Assert.assertEquals(8, links.size());

		List<Link> cloneLinks = links.get("clone");
		Assert.assertEquals(2, cloneLinks.size());

		Link firstCloneLink = cloneLinks.get(0);
		Assert.assertEquals("https://bitbucket.org/phlogistonjohn/tweakmsg", firstCloneLink.getHref());
		Assert.assertEquals("https", firstCloneLink.getName());
	}

	@Test
	public void shouldBeAbleToDeseralizeGetBuildStatus() throws Exception {
		Response response = bitbucketV2Client.getBuildStatus("owner", "repo_slug", "revision", "key");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		BuildStatus buildStatus = response.readEntity(BuildStatus.class);
		Assert.assertEquals(BuildStatus.STATE.SUCCESSFUL, buildStatus.getState());
		Assert.assertEquals("build", buildStatus.getType());
		Assert.assertEquals("BAMBOO-PROJECT-X", buildStatus.getKey());
		Assert.assertEquals("Build #34", buildStatus.getName());
		Assert.assertEquals("https://example.com/path/to/build", buildStatus.getUrl());
		Assert.assertEquals("Changes by John Doe", buildStatus.getDescription());

		Map<String, List<Link>> links = buildStatus.getLinks();
		Assert.assertEquals(2, links.size());

		List<Link> selfLinks = links.get("self");
		Assert.assertEquals(1, selfLinks.size());

		Link firstSelfLink = selfLinks.get(0);
		Assert.assertEquals("http://www.google.com/self", firstSelfLink.getHref());

		List<Link> commitLinks = links.get("commit");
		Assert.assertEquals(1, commitLinks.size());

		Link firstCommitLink = commitLinks.get(0);
		Assert.assertEquals("http://www.google.com/commit", firstCommitLink.getHref());
	}

	@Test
	public void shouldBeAbleToPostBuildStatus() throws Exception {
		BuildStatus buildStatus = new BuildStatus();
		buildStatus.setState(BuildStatus.STATE.FAILED);
		buildStatus.setKey("key");
		buildStatus.setType("build");

		Response response = bitbucketV2Client.postBuildStatus("owner", "repo_slug", "revision", buildStatus);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		Assert.assertEquals(BuildStatus.STATE.FAILED, buildStatus.getState());
	}


	@Test
	public void shouldBeAbleToDeseralizeGetRepositoriesByOwner() throws Exception {
		Response response = bitbucketV2Client.getRepositoriesByOwner("owner");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		RepositoryList repositoryList = response.readEntity(RepositoryList.class);

		Assert.assertEquals(10, (long) repositoryList.getPagelen());

		Repository repository = repositoryList.getValues().get(0);
		Assert.assertEquals("git", repository.getScm());

		Date createdDate = simpleDateTimeFormat.parse("2014-08-13T19:05:22.148829+00:00");
		Assert.assertEquals(createdDate, repository.getCreatedOn());

		Date modifiedDate = simpleDateTimeFormat.parse("2014-08-13T19:05:22.168083+00:00");
		Assert.assertEquals(modifiedDate, repository.getUpdatedOn());

		Map<String, List<Link>> links = repository.getLinks();
		Assert.assertEquals(8, links.size());

		List<Link> cloneLinks = links.get("clone");
		Assert.assertEquals(2, cloneLinks.size());

		Link firstCloneLink = cloneLinks.get(1);
		Assert.assertEquals("ssh://git@bitbucket.org/dans9190/new-repository.git", firstCloneLink.getHref());
		Assert.assertEquals("ssh", firstCloneLink.getName());

		User owner = repository.getOwner();
		Assert.assertEquals("Daniel  Stevens", owner.getDisplayName());

		Map<String, List<Link>> ownerLinks = owner.getLinks();
		Assert.assertEquals(3, ownerLinks.size());
	}

	@Test
	public void shouldBeAbleToDeseralizeRepositoryByOwnerSlug() throws Exception {

		Response response = bitbucketV2Client.getRepositoryByOwnerRepo("owner", "repo_slug");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		Repository repository = response.readEntity(Repository.class);

		Assert.assertEquals("hg", repository.getScm());

		Date createdDate = simpleDateTimeFormat.parse("2011-12-20T16:35:06.480042+00:00");
		Assert.assertEquals(createdDate, repository.getCreatedOn());

		Date modifiedDate = simpleDateTimeFormat.parse("2014-11-03T02:24:08.409995+00:00");
		Assert.assertEquals(modifiedDate, repository.getUpdatedOn());

		Map<String, List<Link>> links = repository.getLinks();
		Assert.assertEquals(8, links.size());

		List<Link> cloneLinks = links.get("clone");
		Assert.assertEquals(2, cloneLinks.size());

		Link firstCloneLink = cloneLinks.get(1);
		Assert.assertEquals("ssh://hg@bitbucket.org/tutorials/tutorials.bitbucket.org", firstCloneLink.getHref());
		Assert.assertEquals("ssh", firstCloneLink.getName());

		User owner = repository.getOwner();
		Assert.assertEquals("tutorials account", owner.getDisplayName());

		Map<String, List<Link>> ownerLinks = owner.getLinks();
		Assert.assertEquals(3, ownerLinks.size());
	}

	@Test
	public void shouldBeAbleToDeseralizePullRequestList() throws Exception {
		Response response = bitbucketV2Client.getPullRequests("owner", "repo_slug", "OPEN");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		PullRequestList pullRequestList = response.readEntity(PullRequestList.class);

		Assert.assertEquals(1, (long)pullRequestList.getPage());
		Assert.assertEquals(12, (long)pullRequestList.getSize());
		Assert.assertEquals(1, (long)pullRequestList.getPagelen());

		List<PullRequest> pullRequests = pullRequestList.getValues();
		Assert.assertEquals(1, pullRequests.size());

		PullRequest firstPullRequest = pullRequests.get(0);
		Assert.assertEquals(true, firstPullRequest.getCloseSourceBranch());

		Map<String, List<Link>> links = firstPullRequest.getLinks();
		Assert.assertEquals(10, links.size());

		CommitSource commitSource = firstPullRequest.getSource();
		Assert.assertEquals("mfrauenholtz/team-removal/admin-links", commitSource.getBranch().getName());
		Assert.assertEquals("2a81a1edc0c2", commitSource.getCommit().getHash());
		Assert.assertEquals(1, commitSource.getCommit().getLinks().size());

		CommitSource commitDestination = firstPullRequest.getDestination();
		Assert.assertEquals("staging", commitDestination.getBranch().getName());
		Assert.assertEquals("e04099ba977c", commitDestination.getCommit().getHash());
		Assert.assertEquals(1, commitDestination.getCommit().getLinks().size());
	}

	@Test
	public void shouldBeAbleToDeseralizePullRequest() throws Exception {
		Response response = bitbucketV2Client.getPullRequestById("owner", "repo_slug", (long)1);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		PullRequest pullRequest = response.readEntity(PullRequest.class);

		Assert.assertEquals(true, pullRequest.getCloseSourceBranch());

		Map<String, List<Link>> links = pullRequest.getLinks();
		Assert.assertEquals(10, links.size());

		CommitSource commitSource = pullRequest.getSource();
		Assert.assertEquals("mfrauenholtz/team-removal/admin-links", commitSource.getBranch().getName());
		Assert.assertEquals("2a81a1edc0c2", commitSource.getCommit().getHash());
		Assert.assertEquals(1, commitSource.getCommit().getLinks().size());

		CommitSource commitDestination = pullRequest.getDestination();
		Assert.assertEquals("staging", commitDestination.getBranch().getName());
		Assert.assertEquals("e04099ba977c", commitDestination.getCommit().getHash());
		Assert.assertEquals(1, commitDestination.getCommit().getLinks().size());

		Assert.assertEquals(1, pullRequest.getReviewers().size());
		Assert.assertEquals("bnguyen", pullRequest.getReviewers().get(0).getUsername());

		Assert.assertEquals(3, pullRequest.getParticipants().size());
		Assert.assertEquals("PARTICIPANT", pullRequest.getParticipants().get(0).getRole());
		Assert.assertEquals("mfrauenholtz", pullRequest.getParticipants().get(0).getUser().getUsername());
		Assert.assertEquals("PARTICIPANT", pullRequest.getParticipants().get(1).getRole());
		Assert.assertEquals("dbennett", pullRequest.getParticipants().get(1).getUser().getUsername());

	}

	@Test
	public void shouldBeAbleToDeseralizeRepoCommitList() throws Exception {
		Response response = bitbucketV2Client.getCommitsByOwnerRepo("owner", "repo_slug");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		CommitList commitList = response.readEntity(CommitList.class);

		Assert.assertEquals(1, (long) commitList.getPage());
		Assert.assertEquals(30, (long) commitList.getPagelen());

		List<Commit> commits = commitList.getValues();
		Assert.assertEquals(1, commits.size());

		Commit firstCommit = commits.get(0);
		Assert.assertEquals("61d9e64348f9da407e62f64726337fd3bb24b466", firstCommit.getHash());

		Map<String, List<Link>> links = firstCommit.getLinks();
		Assert.assertEquals(6, links.size());

		Assert.assertEquals("59721f593b020123a75424285845325126f56e2e", firstCommit.getParents().get(0).getHash());

		Date date = simpleDateTimeFormat2.parse("2013-10-21T07:21:51+00:00");
		Assert.assertEquals(date, firstCommit.getDate());

		Assert.assertEquals("Merge remote-tracking branch 'origin/rest-2.8.x' ", firstCommit.getMessage());

		Assert.assertEquals("Joseph Walton", firstCommit.getAuthor().getUser().getDisplayName());
		Assert.assertEquals("jwalton", firstCommit.getAuthor().getUser().getUsername());
	}

	@Test
	public void shouldBeAbleToDeseralizeRepoCommit() throws Exception {
		Response response = bitbucketV2Client.getCommitByOwnerRepoRevision("owner", "repo_slug", "revision");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		Commit commit = response.readEntity(Commit.class);

		Assert.assertEquals("61d9e64348f9da407e62f64726337fd3bb24b466", commit.getHash());

		Map<String, List<Link>> links = commit.getLinks();
		Assert.assertEquals(6, links.size());

		Assert.assertEquals("59721f593b020123a75424285845325126f56e2e", commit.getParents().get(0).getHash());

		Date date = simpleDateTimeFormat2.parse("2013-10-21T07:21:51+00:00");
		Assert.assertEquals(date, commit.getDate());

		Assert.assertEquals("Merge remote-tracking branch 'origin/rest-2.8.x' ", commit.getMessage());

		Assert.assertEquals("Joseph Walton", commit.getAuthor().getUser().getDisplayName());
		Assert.assertEquals("jwalton", commit.getAuthor().getUser().getUsername());
	}

	@Test
	public void shouldBeAbleToDeseralizePullRequestCommitList() throws Exception {
		Response response = bitbucketV2Client.getPullRequestCommits("owner", "repo_slug", (long)1);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		CommitList commitList = response.readEntity(CommitList.class);

		Assert.assertEquals(1, (long) commitList.getPage());
		Assert.assertEquals(11, (long) commitList.getSize());
		Assert.assertEquals(1, (long) commitList.getPagelen());

		List<Commit> commits = commitList.getValues();
		Assert.assertEquals(1, commits.size());

		Commit firstCommit = commits.get(0);
		Assert.assertEquals("ad758aeba36fa4b9d258ac1667f55cfb811e6df3", firstCommit.getHash());

		Map<String, List<Link>> links = firstCommit.getLinks();
		Assert.assertEquals(6, links.size());

		Assert.assertEquals("16929c2f94828ca9d8adb5b91e7db7b274aae318", firstCommit.getParents().get(0).getHash());

		Date date = simpleDateTimeFormat2.parse("2013-11-07T00:17:05+00:00");
		Assert.assertEquals(date, firstCommit.getDate());

		Assert.assertEquals("Reorder imports. ", firstCommit.getMessage());

		Assert.assertEquals("Erik van Zijst <erik.van.zijst@gmail.com>", firstCommit.getAuthor().getRaw());
		Assert.assertEquals("erik", firstCommit.getAuthor().getUser().getUsername());
	}

	@Test
	public void shouldBeAbleToDeseralizeCommitCommentList() throws Exception {
		Response response = bitbucketV2Client.getCommentsByOwnerRepoRevision("owner", "repo_slug", "revision");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		CommentList commentList = response.readEntity(CommentList.class);

		List<Comment> comments = commentList.getValues();
		Assert.assertEquals(1, comments.size());

		Comment firstComment = comments.get(0);
		Assert.assertEquals(530189, (long)firstComment.getId());


		Date dateCreated = simpleDateTimeFormat.parse("2013-11-07T23:55:24.486865+00:00");
		Assert.assertEquals(dateCreated, firstComment.getCreatedOn());

		Date dateModified = simpleDateTimeFormat.parse("2013-11-07T23:55:24.502477+00:00");
		Assert.assertEquals(dateModified, firstComment.getUpdatedOn());
	}

	@Test
	public void shouldBeAbleToDeseralizeCommitComment() throws Exception {
		Response response = bitbucketV2Client.getCommentByOwnerRepoRevisionId("owner", "repo_slug", "revision",
			"comment_id");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		Comment comment = response.readEntity(Comment.class);

		Assert.assertEquals(530189, (long)comment.getId());

		Date dateCreated = simpleDateTimeFormat.parse("2013-11-07T23:55:24.486865+00:00");
		Assert.assertEquals(dateCreated, comment.getCreatedOn());

		Date dateModified = simpleDateTimeFormat.parse("2013-11-07T23:55:24.502477+00:00");
		Assert.assertEquals(dateModified, comment.getUpdatedOn());
	}

	@Test
	public void shouldBeAbleToDeseralizePullRequestCommentList() throws Exception {
		Response response = bitbucketV2Client.getPullRequestComments("owner", "repo_slug", (long)1);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		CommentList commentList = response.readEntity(CommentList.class);

		Assert.assertEquals(1, (long) commentList.getPage());
		Assert.assertEquals(4, (long) commentList.getSize());
		Assert.assertEquals(1, (long) commentList.getPagelen());

		List<Comment> comments = commentList.getValues();
		Assert.assertEquals(1, comments.size());

		Comment firstComment = comments.get(0);
		Assert.assertEquals(25337, (long)firstComment.getId());

		Assert.assertEquals(25334, (long)firstComment.getParent().getId());

		Assert.assertEquals("markdown", firstComment.getContent().getMarkup());
	}

	@Test
	public void shouldBeAbleToDeseralizePullRequestActivity() throws Exception {
		Response response = bitbucketV2Client.getPullRequestActivity("owner", "repo_slug");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		PullRequestActivityList activityList = response.readEntity(PullRequestActivityList.class);

		Assert.assertEquals(10965, (long)activityList.getSize());
	}

	@Test
	public void shouldBeAbleToDeseralizePullRequestActivityById() throws Exception {
		Response response = bitbucketV2Client.getPullRequestActivityById("owner", "repo_slug", (long)1);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		PullRequestActivityList activityList = response.readEntity(PullRequestActivityList.class);

		Assert.assertEquals(10, (long) activityList.getSize());
	}

	@Test
	public void shouldBeAbleToGetPullRequestDiff() throws Exception {
		Response response = bitbucketV2Client.getPullRequestDiff("owner", "repo_slug", (long)1);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		String diffString = response.readEntity(String.class);
		InputStream diffStream = new ByteArrayInputStream(diffString.getBytes(StandardCharsets.UTF_8));

		DiffParser parser = new UnifiedDiffParser();
		List<Diff> diff = parser.parse(diffStream);
		Assert.assertEquals(5, diff.size());
	}

	@Test
	public void shouldBeAbleToDeseralizeSnippitByUsernameId() throws Exception {
		Response response = bitbucketV2Client.getSnippetByUsernameId("username", "kypj");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		Snippet snippet = response.readEntity(Snippet.class);

		Assert.assertEquals("kypj", snippet.getId());

		Map<String, BBFile> files = snippet.getFiles();
		Assert.assertTrue(files.containsKey("README.md"));
		Assert.assertTrue(files.containsKey("myquote2.html"));
	}

	@Test
	public void shouldBeAbleToDeseralizeTeam() throws Exception {
		Response response = bitbucketV2Client.getTeamByName("teamname");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		Team team = response.readEntity(Team.class);
		Assert.assertEquals("teamsinspace", team.getUsername());
		Assert.assertEquals("Teams In Space", team.getDisplayName());
		Assert.assertEquals("{61fc5cf6-d054-47d2-b4a9-061ccf858379}", team.getUuid());
		Assert.assertEquals(7, team.getLinks().size());

		Date dateCreated = simpleDateTimeFormat.parse("2014-04-08T00:00:14.070969+00:00");
		Assert.assertEquals(dateCreated, team.getCreatedOn());
	}

	@Test
	public void shouldBeAbleToDeseralizeTeamFollowers() throws Exception {
		Response response = bitbucketV2Client.getTeamFollowers("teamname");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		UserList userList = response.readEntity(UserList.class);
		Assert.assertEquals(1, userList.getValues().size());

		User user = userList.getValues().get(0);
		Assert.assertEquals("tutorials", user.getUsername());
	}

	@Test
	public void shouldBeAbleToDeseralizeTeamRepository() throws Exception {
		Response response = bitbucketV2Client.getTeamRepositories("teamname");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		RepositoryList repositoryList = response.readEntity(RepositoryList.class);
		Assert.assertEquals(2, repositoryList.getValues().size());

		Repository repository = repositoryList.getValues().get(0);
		Assert.assertEquals("hg", repository.getScm());
	}

	@Test
	public void shouldBeAbleToDeseralizeTeamFollowing() throws Exception {
		Response response = bitbucketV2Client.getTeamFollowing("teamname");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		UserList userList = response.readEntity(UserList.class);
		Assert.assertEquals(1, userList.getValues().size());

		User user = userList.getValues().get(0);
		Assert.assertEquals("bitbucket", user.getUsername());
	}

	@Test
	public void shouldBeAbleToDeseralizeTeams() throws Exception {
		Response response = bitbucketV2Client.getTeams("contributor");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		UserList teamList = response.readEntity(UserList.class);
		Assert.assertEquals(10, (long)teamList.getPagelen());

		List<User> users = teamList.getValues();
		Assert.assertEquals(2, users.size());

		User firstUser = users.get(0);
		Assert.assertEquals("1team", firstUser.getUsername());
		Assert.assertEquals("the team", firstUser.getDisplayName());
	}

	@Test
	public void shouldBeAbleToDeseralizeTeamMembers() throws Exception {
		Response response = bitbucketV2Client.getTeamMembers("teamname");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		UserList userList = response.readEntity(UserList.class);
		Assert.assertEquals(1, userList.getValues().size());

		User user = userList.getValues().get(0);
		Assert.assertEquals("Alana-dev", user.getUsername());
	}

	@Test
	public void shouldBeAbleToDeseralizeUser() throws Exception {
		Response response = bitbucketV2Client.getUser("username");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		User user = response.readEntity(User.class);

		Assert.assertEquals("tutorials", user.getUsername());
		Assert.assertEquals("https://tutorials.bitbucket.org/", user.getWebsite());
		Assert.assertEquals("tutorials account", user.getDisplayName());
		Assert.assertEquals("Santa Monica, CA", user.getLocation());
		Assert.assertEquals("user", user.getType());

		Map<String, List<Link>> links = user.getLinks();
		Assert.assertEquals(6, links.size());

		Date dateModified = simpleDateTimeFormat.parse("2011-12-20T16:34:07.132459+00:00");
		Assert.assertEquals(dateModified, user.getCreatedOn());
	}

	@Test
	public void shouldBeAbleToDeseralizeUserByUsername() throws Exception {
		Response response = bitbucketV2Client.getUser("username");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		User user = response.readEntity(User.class);

		Assert.assertEquals("tutorials", user.getUsername());
		Assert.assertEquals("https://tutorials.bitbucket.org/", user.getWebsite());
		Assert.assertEquals("tutorials account", user.getDisplayName());
		Assert.assertEquals("Santa Monica, CA", user.getLocation());
		Assert.assertEquals("user", user.getType());

		Map<String, List<Link>> links = user.getLinks();
		Assert.assertEquals(6, links.size());

		Date dateModified = simpleDateTimeFormat.parse("2011-12-20T16:34:07.132459+00:00");
		Assert.assertEquals(dateModified, user.getCreatedOn());
	}

	@Test
	public void shouldBeAbleToDeseralizeUserFollowers() throws Exception {
		Response response = bitbucketV2Client.getUserFollowers("username");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		UserFollowerList followerList = response.readEntity(UserFollowerList.class);

		Assert.assertEquals(10, (long)followerList.getPagelen());
		Assert.assertEquals(1, (long) followerList.getPage());
		Assert.assertEquals(1, (long)followerList.getSize());

		List<User> followers = followerList.getValues();
		Assert.assertEquals(1, followers.size());

		User firstFollower = followers.get(0);
		Assert.assertEquals("tutorials", firstFollower.getUsername());
	}

	@Test
	public void shouldBeAbleToPostComment() throws Exception {
		Response response = bitbucketV1Client.postPullRequestComment("owner", "repo_slug", 1L, "comment");
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		V1Comment v1Comment = response.readEntity(V1Comment.class);
		Assert.assertEquals(672, (long)v1Comment.getPullRequestId());
	}

	@Test
	public void shouldBeAbleToPostCommentObject() throws Exception {
		V1Comment comment = new V1Comment();
		comment.setLineTo(59);
		comment.setFilename("/test/file.xml");
		comment.setContent("test comment");

		Response response = bitbucketV1Client.postPullRequestComment("owner", "repo_slug", 1L, comment);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		V1Comment v1Comment = response.readEntity(V1Comment.class);
		Assert.assertEquals(672, (long)v1Comment.getPullRequestId());
	}

	@Test
	public void shouldBeAbleToUpdateComment() throws Exception {
		V1Comment comment = new V1Comment();
		comment.setLineTo(59);
		comment.setFilename("/test/file.xml");
		comment.setContent("test comment");

		Response response = bitbucketV1Client.putPullRequestComment("owner", "repo_slug", 1L, comment);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		V1Comment v1Comment = response.readEntity(V1Comment.class);
		Assert.assertEquals(672L, (long)v1Comment.getPullRequestId());
	}

	private static void addFilesToWebArchive(WebArchive war, File dir) throws IllegalArgumentException {
		if (dir == null || !dir.isDirectory()) {
			throw new IllegalArgumentException("not a directory");
		}
		for (File f : dir.listFiles()) {
			if (f.isFile()) {
				war.addAsWebResource(f, f.getPath().replace("\\", "/").substring("src/test/resources/".length()));
			} else {
				addFilesToWebArchive(war, f);
			}
		}
	}

	private static void addResourceFilesToArchive(WebArchive war, File dir) throws IllegalArgumentException {
		if (dir == null || !dir.isDirectory()) {
			throw new IllegalArgumentException("not a directory");
		}
		for (File f : dir.listFiles()) {
			if (f.isFile()) {
				war.addAsResource(f, f.getPath().replace("\\", "/").substring("src/test/resources/".length()));
			} else {
				addResourceFilesToArchive(war, f);
			}
		}
	}

}
