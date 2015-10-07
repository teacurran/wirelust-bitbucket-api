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
import com.wirelust.bitbucket.client.representations.Link;
import com.wirelust.bitbucket.client.representations.Repository;
import com.wirelust.bitbucket.client.representations.RepositoryList;
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
	}

	@After
	public void destroy() {
	}

	@Test
	public void deserializeUserActivitiesGoalsDaily() throws Exception {

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
