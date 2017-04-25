package test.service.impl.activityservice;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.config.JavastravaApplicationConfig;
import javastrava.model.StravaComment;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import test.api.APITest;
import test.service.standardtests.DeleteMethodTest;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.CommentDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentTest extends DeleteMethodTest<StravaComment, Integer> {
	@Override
	protected CreateCallback<StravaComment> creator() {
		return CommentDataUtils.stravaCreator();
	}

	@Override
	protected DeleteCallback<StravaComment> deleter() {
		return CommentDataUtils.deleter();
	}

	@Override
	protected StravaComment generateInvalidObject() {
		return CommentDataUtils.generateInvalidObject();
	}

	@Override
	protected StravaComment generateValidObject() {
		return CommentDataUtils.generateValidObject();
	}

	@Override
	protected GetCallback<StravaComment, Integer> getter() {
		return CommentDataUtils.getter();
	}

	/**
	 * <p>
	 * Delete by comment ids
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDeleteComment_byIds() throws Exception {
		// Can only perform the test if application-level permission is on
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {

			RateLimitedTestRunner.run(() -> {
				final StravaComment comment = TestUtils.stravaWithWriteAccess().createComment(CommentDataUtils.generateValidObject());
				TestUtils.stravaWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());
			});
		}
	}

	/**
	 * Can we delete a comment on a private activity belonging to the authenticated user (create activity, create comment, make activity private, try to delete comment)
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@Test
	public void testDeleteComment_privateActivityAuthenticatedUser() throws Exception {
		// Can only perform the test if application-level permission is on
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {

			RateLimitedTestRunner.run(() -> {
				final StravaComment comment = APITest.createPrivateActivityWithComment("DeleteCommentTest.testDeleteComment_privateActivityAuthenticatedUser"); //$NON-NLS-1$

				// Attempt to delete with full access
				try {
					TestUtils.stravaWithFullAccess().deleteComment(comment);
					return;
				} catch (final Exception e) {
					forceDelete(comment);
					throw e;
				}
			});
		}
	}

	/**
	 * Can we delete a comment on a private activity belonging to the authenticated user when the token does not have VIEW_PRIVATE scope (create activity, create comment, make activity private, try to
	 * delete comment)
	 *
	 * @throws Exception
	 *             if test fails
	 */
	@Test
	public void testDeleteComment_privateActivityNoViewPrivate() throws Exception {
		// Can only perform the test if application-level permission is on
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {

			RateLimitedTestRunner.run(() -> {
				final StravaComment comment = APITest.createPrivateActivityWithComment("DeleteCommentTest.testDeleteComment_privateActivityNoViewPrivate"); //$NON-NLS-1$

				// Attempt to delete with write access (but not view_private)
				try {
					TestUtils.stravaWithWriteAccess().deleteComment(comment);
					fail("Deleted a comment on a private activity, but don't have VIEW_PRIVATE scope"); //$NON-NLS-1$
				} catch (final UnauthorizedException e) {
					// Expected
					forceDelete(comment);
					return;
				} catch (final Exception e) {
					forceDelete(comment);
					throw e;
				}
			});
		}
	}

	@Override
	@Test
	public void testDeleteNonExistentParent() throws Exception {
		// Can only perform the test if application-level permission is on
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {

			RateLimitedTestRunner.run(() -> {
				final List<StravaComment> comments = TestUtils.stravaWithFullAccess().listActivityComments(ActivityDataUtils.ACTIVITY_WITH_COMMENTS);
				final StravaComment comment = comments.get(0);
				comment.setActivityId(ActivityDataUtils.ACTIVITY_INVALID);

				try {
					deleter().delete(TestUtils.stravaWithFullAccess(), comment);
				} catch (final NotFoundException e) {
					// Expected
					return;
				}

				// Fail
				fail("Deleted a comment for a non-existent activity??"); //$NON-NLS-1$
			});
		}
	}

	@Test
	@Override
	public void testDeleteNoWriteAccess() throws Exception {
		// Can only perform the test if application-level permission is on
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {

			RateLimitedTestRunner.run(() -> {
				final StravaComment comment = TestUtils.stravaWithWriteAccess().createComment(CommentDataUtils.generateValidObject());
				try {
					TestUtils.strava().deleteComment(comment);
				} catch (final UnauthorizedException e) {
					// Expected - delete the comment anyway
					forceDelete(comment);
					return;
				}
				fail("Deleted a comment using a token without write access"); //$NON-NLS-1$
			});
		}
	}

	/**
	 * @see test.service.standardtests.DeleteMethodTest#testDeleteValidObject()
	 */
	@Test
	@Override
	public void testDeleteValidObject() throws Exception {
		// Can only perform the test if application-level permission is on
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			super.testDeleteValidObject();
		}
	}

	@Override
	@Test
	public void testInvalidId() throws Exception {
		// Can only perform the test if application-level permission is on
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {

			RateLimitedTestRunner.run(() -> {
				try {
					TestUtils.stravaWithFullAccess().deleteComment(ActivityDataUtils.ACTIVITY_INVALID, new Integer(0));
				} catch (final NotFoundException e) {
					// Expected
					return;
				}

				// Fail
				fail("Succeffully deleted comment with invalid id!"); //$NON-NLS-1$
			});
		}
	}

	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Can only perform the test if application-level permission is on
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {

			// can't currently test this
			return;
		}
	}

	@Override
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Can only perform the test if application-level permission is on
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {

			RateLimitedTestRunner.run(() -> {
				// Set up test data
				final StravaComment comment = CommentDataUtils.generateValidObject();
				comment.setActivityId(ActivityDataUtils.ACTIVITY_PRIVATE);
				final StravaComment createdComment = TestUtils.stravaWithFullAccess().createComment(comment);

				// Attempt to delete it without private scope
				try {
					TestUtils.stravaWithWriteAccess().deleteComment(createdComment);
				} catch (final UnauthorizedException e) {
					// Now delete it again
					forceDelete(createdComment);
					return;
				}

				// If we get here then the comment was successfully deleted in error
				fail("Comment on a private activity was successfully deleted, but token did not have view_private scope"); //$NON-NLS-1$
			});
		}
	}

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Can only perform the test if application-level permission is on
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {

			RateLimitedTestRunner.run(() -> {
				// Set up test data
				final StravaComment comment = CommentDataUtils.generateValidObject();
				comment.setActivityId(ActivityDataUtils.ACTIVITY_PRIVATE);
				final StravaComment createdComment = TestUtils.stravaWithFullAccess().createComment(comment);

				// Now delete it again
				TestUtils.stravaWithFullAccess().deleteComment(createdComment);
			});
		}
	}

	@Override
	protected void validate(StravaComment object) {
		CommentDataUtils.validateComment(object);
	}

}
