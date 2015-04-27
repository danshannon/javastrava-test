package test.api.rest.activity.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaCommentTest;
import test.api.rest.APITest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodAsyncTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListActivityCommentsAsyncTest extends PagingArrayMethodAsyncTest<StravaComment, Integer> {
	@Override
	protected ArrayCallback<StravaComment> callback() throws Exception {
		return (paging -> api().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, null, paging.getPage(), paging.getPageSize()).get());
	}

	@Test
	public void testListActivityComments_activityMarkdownPaging() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment[] comments = api().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.TRUE, 1, 1).get();
			assertNotNull(comments);
			assertEquals(1, comments.length);
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
			final StravaComment[] comments = api().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.TRUE, null, null).get();

			assertNotNull("Returned null list of comments (with markdown) when some were expected");
			assertNotEquals("Returned empty list of comments when some were expected", 0, comments.length);

			final StravaComment[] commentsWithoutMarkdown = api().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE, null, null).get();

			// Check that the lists are the same length!!
			assertNotNull("Returned null list of comments (without markdown) when some were expected");
			assertEquals("List of comments for activity " + TestUtils.ACTIVITY_WITH_COMMENTS + " is not same length with/without markdown!",
						comments.length, commentsWithoutMarkdown.length);
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
			final StravaComment[] comments = api().listActivityComments(TestUtils.ACTIVITY_WITHOUT_COMMENTS, Boolean.TRUE, null, null).get();

			assertNotNull("Returned null list of comments when an empty array was expected", comments);
			assertEquals("Returned a non-empty list of comments when none were expected", 0, comments.length);
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
			try {
				api().listActivityComments(TestUtils.ACTIVITY_INVALID, Boolean.FALSE, null, null).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned comments for a non-existent activity");
		});
	}

	@Test
	public void testListActivityComments_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listActivityComments(TestUtils.ACTIVITY_PRIVATE_OTHER_USER, null, null, null).get();
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned comments for a private activity belonging to another user");
		});
	}

	@Test
	public void testListActivityComments_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = APITest
					.createPrivateActivityWithComment("ListActivityCommentsTest.testListActivityComments_privateWithoutViewPrivate()");
			try {
				api().listActivityComments(comment.getActivityId(), null, null, null).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned comments for a private activity without view_private access");
		});
	}

	@Test
	public void testListActivityComments_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = APITest
					.createPrivateActivityWithComment("ListActivityCommentsTest.testListActivityComments_privateWithViewPrivate()");
			final StravaComment[] comments = apiWithViewPrivate().listActivityComments(comment.getActivityId(), null, null, null).get();
			forceDeleteActivity(comment.getActivityId());
			assertNotNull(comments);
			assertEquals(1, comments.length);
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
