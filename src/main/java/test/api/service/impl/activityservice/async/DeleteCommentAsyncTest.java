package test.api.service.impl.activityservice.async;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.rest.APITest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentAsyncTest extends StravaTest {
	@Test
	public void testDeleteComment_byComment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			stravaWithWriteAccess().deleteCommentAsync(comment).get();
		});
	}

	@Test
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			stravaWithWriteAccess().deleteCommentAsync(comment.getActivityId(), comment.getId()).get();
		});
	}

	@Test
	public void testDeleteComment_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			try {
				strava().deleteCommentAsync(comment).get();
			} catch (final UnauthorizedException e) {
				// Expected - delete the comment anyway
				forceDeleteComment(comment);
				return;
			}
			fail("Deleted a comment using a token without write access");
		});
	}

	/**
	 * Can we delete a comment on a private activity belonging to the authenticated user (create activity, create comment, make activity private, try to delete
	 * comment)
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteComment_privateActivityAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = APITest.createPrivateActivityWithComment("DeleteCommentTest.testDeleteComment_privateActivityAuthenticatedUser");

			// Attempt to delete with full access
			try {
				stravaWithFullAccess().deleteCommentAsync(comment).get();
				forceDeleteActivity(comment.getActivityId());
				return;
			} catch (final Exception e) {
				forceDeleteActivity(comment.getActivityId());
				throw e;
			}
		});
	}

	/**
	 * Can we delete a comment on a private activity belonging to the authenticated user when the token does not have VIEW_PRIVATE scope (create activity,
	 * create comment, make activity private, try to delete comment)
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteComment_privateActivityNoViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = APITest.createPrivateActivityWithComment("DeleteCommentTest.testDeleteComment_privateActivityNoViewPrivate");

			// Attempt to delete with write access (but not view_private)
			try {
				stravaWithWriteAccess().deleteCommentAsync(comment).get();
				forceDeleteActivity(comment.getActivityId());
				fail("Deleted a comment on a private activity, but don't have VIEW_PRIVATE scope");
			} catch (final UnauthorizedException e) {
				// Expected
				forceDeleteActivity(comment.getActivityId());
				return;
			} catch (final Exception e) {
				forceDeleteActivity(comment.getActivityId());
				throw e;
			}
		});
	}
}
