package test.api.service.impl.retrofit.activityservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.ActivityServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesImpl;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaCommentTest;
import test.api.service.impl.ListCallback;
import test.api.service.impl.PagingListMethodTest;
import test.utils.TestUtils;

public class ListActivityCommentsTest extends PagingListMethodTest<StravaComment,Integer> {
	/**
	 * <p>
	 * List {@link StravaComment comments} for a valid activity
	 * </p>
	 *
	 * <p>
	 * Expectation is that at least one of the comments contains Markdown; this is tested by checking that at least one comment is different
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_hasComments() {
		final List<StravaComment> comments = service().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.TRUE);

		assertNotNull("Returned null list of comments (with markdown) when some were expected");
		assertNotEquals("Returned empty list of comments when some were expected", 0, comments.size());

		final List<StravaComment> commentsWithoutMarkdown = service().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE);

		// Check that the lists are the same length!!
		assertNotNull("Returned null list of comments (without markdown) when some were expected");
		assertEquals("List of comments for activity " + TestUtils.ACTIVITY_WITH_COMMENTS + " is not same length with/without markdown!", comments.size(),
				commentsWithoutMarkdown.size());
		for (final StravaComment comment : comments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			StravaCommentTest.validateComment(comment, comment.getId(), comment.getResourceState());
		}
		for (final StravaComment comment : commentsWithoutMarkdown) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			StravaCommentTest.validateComment(comment, comment.getId(), comment.getResourceState());
		}
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
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_hasNoComments() {
		final List<StravaComment> comments = service().listActivityComments(TestUtils.ACTIVITY_WITHOUT_COMMENTS, Boolean.TRUE);

		assertNotNull("Returned null list of comments when an empty array was expected", comments);
		assertEquals("Returned a non-empty list of comments when none were expected", 0, comments.size());
		for (final StravaComment comment : comments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			StravaCommentTest.validateComment(comment, comment.getId(), comment.getResourceState());
		}
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
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_invalidActivity() {
		final ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		List<StravaComment> comments;
		comments = service.listActivityComments(TestUtils.ACTIVITY_INVALID, Boolean.FALSE);

		assertNull("Expected null response when retrieving comments for an invalid activity", comments);
	}

	@Test
	public void testListActivityComments_privateActivity() {
		final ActivityServices service = service();
		final List<StravaComment> comments = service.listActivityComments(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
		assertNotNull(comments);
		assertEquals(0, comments.size());
	}

	@Override
	protected void validate(final StravaComment comment, final Integer id, final StravaResourceState state) {
		StravaCommentTest.validateComment(comment, id, state);

	}

	@Override
	protected void validate(final StravaComment comment) {
		validate(comment, comment.getId(), comment.getResourceState());

	}

	private ActivityServices service() {
		return ActivityServicesImpl.implementation(TestUtils.getValidToken());
	}

	@Override
	protected ListCallback<StravaComment> callback() {
		return (new ListCallback<StravaComment>() {

			@Override
			public List<StravaComment> getList(final Paging paging) {
				return service().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS,paging);
			}

		});
	}

}
