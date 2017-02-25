package test.api.service.impl.activityservice;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.APITest;
import test.api.service.standardtests.DeleteMethodTest;
import test.api.service.standardtests.callbacks.DeleteCallback;
import test.api.service.standardtests.callbacks.GetCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentTest extends DeleteMethodTest<StravaComment, Integer, Integer> {
	@Test
	public void testDeleteComment_byComment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			stravaWithWriteAccess().deleteComment(comment);
		});
	}

	@Test
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			stravaWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());
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
				stravaWithFullAccess().deleteComment(comment);
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
				stravaWithWriteAccess().deleteComment(comment);
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

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#getIdPrivateBelongsToOtherUser()
	 */
	@Override
	public Integer getIdPrivateBelongsToOtherUser() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#getIdPrivateBelongsToAuthenticatedUser()
	 */
	@Override
	public Integer getIdPrivateBelongsToAuthenticatedUser() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.spec.StandardTests#getInvalidId()
	 */
	@Override
	public Integer getInvalidId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.spec.StandardTests#getValidId()
	 */
	@Override
	public Integer getValidId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.DeleteMethodTest#lister()
	 */
	@Override
	protected DeleteCallback<StravaComment, Integer, Integer> deleteCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.DeleteMethodTest#getter()
	 */
	@Override
	protected GetCallback<StravaComment, Integer> getCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.DeleteMethodTest#getParentIdPrivateBelongsToOtherUser()
	 */
	@Override
	protected Integer getParentIdPrivateBelongsToOtherUser() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.DeleteMethodTest#getParentIdPrivateBelongsToAuthenticatedUser()
	 */
	@Override
	protected Integer getParentIdPrivateBelongsToAuthenticatedUser() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.DeleteMethodTest#createTestData(java.lang.Object)
	 */
	@Override
	protected StravaComment createTestData(final Integer parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.DeleteMethodTest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaComment object) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see test.api.service.standardtests.DeleteMethodTest#validate(java.lang.Object, java.lang.Object, javastrava.api.v3.model.reference.StravaResourceState)
	 */
	@Override
	protected void validate(final StravaComment object, final Integer id, final StravaResourceState state) {
		// TODO Auto-generated method stub

	}
}
