package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaCommentTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListActivityCommentsTest extends PagingListMethodTest<StravaComment, Integer> {
	@Override
	protected ListCallback<StravaComment> callback() {
		return (paging -> strava().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, paging));
	}

	@Test
	public void testListActivityComments_activityMarkdownPaging() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = strava().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.TRUE, new Paging(1, 1));
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
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_hasComments() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = strava().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.TRUE);

			assertNotNull("Returned null list of comments (with markdown) when some were expected");
			assertNotEquals("Returned empty list of comments when some were expected", 0, comments.size());

			final List<StravaComment> commentsWithoutMarkdown = strava().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE);

			// Check that the lists are the same length!!
				assertNotNull("Returned null list of comments (without markdown) when some were expected");
				assertEquals("List of comments for activity " + TestUtils.ACTIVITY_WITH_COMMENTS + " is not same length with/without markdown!",
						comments.size(), commentsWithoutMarkdown.size());
				for (final StravaComment comment1 : comments) {
					assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment1.getActivityId());
					StravaCommentTest.validateComment(comment1, comment1.getId(), comment1.getResourceState());
				}
				for (final StravaComment comment2 : commentsWithoutMarkdown) {
					assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment2.getActivityId());
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
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_hasNoComments() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = strava().listActivityComments(TestUtils.ACTIVITY_WITHOUT_COMMENTS, Boolean.TRUE);

			assertNotNull("Returned null list of comments when an empty array was expected", comments);
			assertEquals("Returned a non-empty list of comments when none were expected", 0, comments.size());
			for (final StravaComment comment : comments) {
				assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
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
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaComment> comments;
			comments = strava().listActivityComments(TestUtils.ACTIVITY_INVALID, Boolean.FALSE);

			assertNull("Expected null response when retrieving comments for an invalid activity", comments);
		});
	}

	@Test
	public void testListActivityComments_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = strava().listActivityComments(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
			assertNotNull(comments);
			assertEquals(0, comments.size());
		});
	}

	@Override
	protected void validate(final StravaComment comment) {
		validate(comment, comment.getId(), comment.getResourceState());

	}

	@Override
	protected void validate(final StravaComment comment, final Integer id, final StravaResourceState state) {
		StravaCommentTest.validateComment(comment, id, state);

	}

}
