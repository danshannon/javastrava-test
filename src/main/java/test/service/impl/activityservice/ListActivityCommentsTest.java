package test.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaComment;
import javastrava.util.Paging;
import test.api.model.StravaCommentTest;
import test.api.rest.APITest;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for list activity comments methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityCommentsTest extends PagingListMethodTest<StravaComment, Long> {
	@SuppressWarnings("static-method")
	private void forceDelete(StravaComment comment) {
		TestUtils.stravaWithFullAccess().deleteComment(comment);
	}

	@Override
	protected Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_WITH_COMMENTS;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_COMMENTS;
	}

	@Override
	protected ListCallback<StravaComment, Long> lister() {
		return ((strava, id) -> strava.listActivityComments(id));
	}

	@Override
	protected PagingListCallback<StravaComment, Long> pagingLister() {
		return ((strava, paging, id) -> strava.listActivityComments(id, paging));
	}

	/**
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testListActivityComments_activityMarkdownPaging() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = TestUtils.strava().listActivityComments(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, Boolean.TRUE, new Paging(1, 1));
			assertNotNull(comments);
			assertEquals(1, comments.size());
		});
	}

	/**
	 * <p>
	 * List {@link StravaComment comments} for a valid activity
	 * </p>
	 *
	 * <p>
	 * Expectation is that at least one of the comments contains Markdown; this is tested by checking that at least one comment is different
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListActivityComments_hasComments() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = TestUtils.strava().listActivityComments(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, Boolean.TRUE);

			assertNotNull("Returned null list of comments (with markdown) when some were expected"); //$NON-NLS-1$
			assertNotEquals("Returned empty list of comments when some were expected", 0, comments.size()); //$NON-NLS-1$

			final List<StravaComment> commentsWithoutMarkdown = TestUtils.strava().listActivityComments(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE);

			// Check that the lists are the same length!!
			assertNotNull("Returned null list of comments (without markdown) when some were expected"); //$NON-NLS-1$
			assertEquals("List of comments for activity " + ActivityDataUtils.ACTIVITY_WITH_COMMENTS //$NON-NLS-1$
					+ " is not same length with/without markdown!", comments.size(), commentsWithoutMarkdown.size()); //$NON-NLS-1$
			for (final StravaComment comment1 : comments) {
				assertEquals(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, comment1.getActivityId());
				StravaCommentTest.validateComment(comment1, comment1.getId(), comment1.getResourceState());
			}
			for (final StravaComment comment2 : commentsWithoutMarkdown) {
				assertEquals(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, comment2.getActivityId());
				StravaCommentTest.validateComment(comment2, comment2.getId(), comment2.getResourceState());
			}
		});
	}

	/**
	 * <p>
	 * List {@link StravaComment comments} for a valid activity which has no comments
	 * </p>
	 *
	 * <p>
	 * Should return an empty array of comments
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListActivityComments_hasNoComments() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = TestUtils.strava().listActivityComments(ActivityDataUtils.ACTIVITY_WITHOUT_COMMENTS, Boolean.TRUE);

			assertNotNull("Returned null list of comments when an empty array was expected", comments); //$NON-NLS-1$
			assertEquals("Returned a non-empty list of comments when none were expected", 0, comments.size()); //$NON-NLS-1$
			for (final StravaComment comment : comments) {
				assertEquals(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
				StravaCommentTest.validateComment(comment, comment.getId(), comment.getResourceState());
			}
		});
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaComment comments} for a non-existent {@link StravaActivity}
	 * </p>
	 *
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListActivityComments_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaComment> comments;
			comments = TestUtils.strava().listActivityComments(ActivityDataUtils.ACTIVITY_INVALID, Boolean.FALSE);

			assertNull("Expected null response when retrieving comments for an invalid activity", comments); //$NON-NLS-1$
		});
	}

	/**
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListActivityComments_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = TestUtils.strava().listActivityComments(ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER);
			assertNotNull(comments);
			assertEquals(0, comments.size());
		});
	}

	/**
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Test
	public void testListActivityComments_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = APITest.createPrivateActivityWithComment("ListActivityCommentsTest.testListActivityComments_privateWithoutViewPrivate()"); //$NON-NLS-1$
			final List<StravaComment> comments = TestUtils.strava().listActivityComments(comment.getActivityId());
			forceDelete(comment);
			assertNotNull(comments);
			assertEquals(0, comments.size());
		});
	}

	/**
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Test
	public void testListActivityComments_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = APITest.createPrivateActivityWithComment("ListActivityCommentsTest.testListActivityComments_privateWithViewPrivate()"); //$NON-NLS-1$
			final List<StravaComment> comments = TestUtils.stravaWithViewPrivate().listActivityComments(comment.getActivityId());
			forceDelete(comment);
			assertNotNull(comments);
			assertEquals(1, comments.size());
		});
	}

	@Override
	protected void validate(final StravaComment comment) {
		StravaCommentTest.validateComment(comment);

	}

}
