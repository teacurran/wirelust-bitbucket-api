package test.com.wirelust.bitbucket.client;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.wirelust.bitbucket.client.BitbucketV2Client;
import com.wirelust.bitbucket.client.Constants;
import com.wirelust.bitbucket.client.representations.Commit;
import com.wirelust.bitbucket.client.representations.CommitList;
import com.wirelust.bitbucket.client.representations.CommitSource;
import com.wirelust.bitbucket.client.representations.Link;
import com.wirelust.bitbucket.client.representations.PullRequest;
import com.wirelust.bitbucket.client.representations.PullRequestList;
import com.wirelust.bitbucket.client.representations.Repository;
import com.wirelust.bitbucket.client.representations.RepositoryList;
import com.wirelust.bitbucket.client.representations.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
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
	static final String DATE_FORMAT = "yyyy-MM-dd";
	static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX";

	static final String MOCK_USER_ID = "-";

	ResteasyClient client;
	ResteasyWebTarget target;
	BitbucketV2Client bitbucketV2Client;
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

		testWar.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importDependencies(ScopeType.TEST)
			.resolve().withTransitivity().asFile());

		System.out.println("test.war:" + testWar.toString(true));
		LOGGER.debug("test deployment: {}", testWar.toString(true));

		return testWar;
	}

	@Before
	public void init() {
		client = new ResteasyClientBuilder().build();
		client.register(JacksonConfigurationProvider.class);
		target = client.target(ROOT_URL);
		bitbucketV2Client = target.proxy(BitbucketV2Client.class);

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

		Response response = bitbucketV2Client.getRepositoryByOwnerRepoSlug("owner", "repo_slug");
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
		Response response = bitbucketV2Client.getPullRequestById("owner", "repo_slug", "id");
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
	public void shouldBeAbleToDeseralizePullRequestCommitList() throws Exception {
		Response response = bitbucketV2Client.getPullRequestCommits("owner", "repo_slug", "id");
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
