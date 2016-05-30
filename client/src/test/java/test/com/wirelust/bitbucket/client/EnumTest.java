package test.com.wirelust.bitbucket.client;

import com.wirelust.bitbucket.client.representations.BuildStatus;
import com.wirelust.bitbucket.client.representations.PullRequest;
import com.wirelust.bitbucket.client.representations.Update;
import com.wirelust.bitbucket.client.representations.v1.Privilege;
import org.junit.Assert;
import org.junit.Test;

/**
 * Date: 31-May-2016
 *
 * @author T. Curran
 */
public class EnumTest {

	@Test
	public void shouldBeAbleToGetPrivilegeTypes() {
		Assert.assertEquals(Privilege.Type.ADMIN, Privilege.Type.fromString("admin"));
		Assert.assertEquals(Privilege.Type.READ, Privilege.Type.fromString("read"));
		Assert.assertEquals(Privilege.Type.WRITE, Privilege.Type.fromString("write"));

		Assert.assertEquals(Privilege.Type.ADMIN.getValue(), "admin");
		Assert.assertEquals(Privilege.Type.READ.getValue(), "read");
		Assert.assertEquals(Privilege.Type.WRITE.getValue(), "write");

		Assert.assertNull(Privilege.Type.fromString(null));
	}

	@Test
	public void shouldBeAbleToGetPullRequestState() {
		Assert.assertEquals(PullRequest.State.OPEN, PullRequest.State.fromString("open"));
		Assert.assertEquals(PullRequest.State.DECLINED, PullRequest.State.fromString("declined"));
		Assert.assertEquals(PullRequest.State.MERGED, PullRequest.State.fromString("merged"));
		Assert.assertNull(PullRequest.State.fromString(null));
	}

	@Test
	public void shouldBeAbleToGetUpdateState() {
		Assert.assertEquals(Update.State.OPEN, Update.State.fromString("open"));
		Assert.assertEquals(Update.State.DECLINED, Update.State.fromString("declined"));
		Assert.assertEquals(Update.State.MERGED, Update.State.fromString("merged"));
		Assert.assertNull(Update.State.fromString(null));
	}

	@Test
	public void shouldBeAbleToBuildStatusState() {
		Assert.assertEquals(BuildStatus.State.INPROGRESS, BuildStatus.State.valueOf("INPROGRESS"));
		Assert.assertEquals(BuildStatus.State.SUCCESSFUL, BuildStatus.State.valueOf("SUCCESSFUL"));
		Assert.assertEquals(BuildStatus.State.FAILED, BuildStatus.State.valueOf("FAILED"));
	}
}
