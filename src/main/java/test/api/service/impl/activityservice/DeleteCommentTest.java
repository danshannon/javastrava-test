package test.api.service.impl.activityservice;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentTest extends StravaTest {
	@Test
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			stravaWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());
		});
	}

	@Test
	public void testDeleteComment_byComment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			stravaWithWriteAccess().deleteComment(comment);
		});
	}

	@Test
	public void testDeleteComment_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			try {
				strava().deleteComment(comment);
			} catch (final UnauthorizedException e) {
				// Expected - delete the comment anyway
				forceDeleteComment(comment);
				return;
			}
			fail("Deleted a comment using a token without write access");
		});
	}
	
	/**
	 * Can we delete a comment on a private activity belonging to the authenticated user (create activity, create comment, make activity private, try to delete comment)
	 * @throws Exception
	 */
	@Test
	public void testDeleteComment_privateActivityAuthenticatedUser() throws Exception {
		fail("Not yet implemented!");
	}
	
	/**
	 * Can we delete a comment on a private activity belonging to the authenticated user when the token does not have VIEW_PRIVATE scope (create activity, create comment, make activity private, try to delete comment)
	 * @throws Exception
	 */
	@Test
	public void testDeleteComment_privateActivityNoViewPrivate() throws Exception {
		fail("Not yet implemented!");
	}
}
