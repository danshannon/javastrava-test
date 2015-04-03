package test.api.rest.activity;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.rest.APITest;
import test.issues.strava.Issue63;
import test.issues.strava.Issue74;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentTest extends APITest {
	@Test
	public void testDeleteComment_byComment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = apiWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			apiWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());
		});
	}

	@Test
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = apiWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			apiWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());
		});
	}

	@Test
	public void testDeleteComment_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#63
				if (new Issue63().isIssue()) {
					return;
				}
				// End of workaround

				final StravaComment comment = apiWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
				try {
					api().deleteComment(comment.getActivityId(), comment.getId());
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
					apiWithFullAccess().deleteComment(comment.getActivityId(), comment.getId());
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
			// TODO This is a workaround for issue javastravaapiv3#74
			if (new Issue74().isIssue()) {
				return;
			}
			// End of workaround

			final StravaComment comment = APITest.createPrivateActivityWithComment("DeleteCommentTest.testDeleteComment_privateActivityNoViewPrivate");

			// Attempt to delete with write access (but not view_private)
				try {
					apiWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());
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
