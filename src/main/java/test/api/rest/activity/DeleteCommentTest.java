package test.api.rest.activity;

import org.junit.Test;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import test.api.model.StravaCommentTest;
import test.api.rest.APIDeleteTest;
import test.api.rest.APITest;
import test.api.rest.callback.TestDeleteCallback;
import test.issues.strava.Issue63;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Tests for {@link API#deleteComment(Long, Integer)} method
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class DeleteCommentTest extends APIDeleteTest<StravaComment, Long> {

	@Override
	public TestDeleteCallback<StravaComment, Long> deleter() {
		return ((api, comment, id) -> {
			api.deleteComment(id, comment.getId());
			return comment;
		});
	}

	@Override
	protected StravaComment createObject() {
		return apiWithWriteAccess().createComment(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, "DeleteCommentTest - please ignore"); //$NON-NLS-1$
	}

	@Override
	public void delete_validParentNoWriteAccess() throws Exception {
		if (new Issue63().isIssue()) {
			return;
		}
		super.delete_validParentNoWriteAccess();
	}

	@Override
	protected void forceDelete(final StravaComment comment) {
		APITest.forceDeleteComment(comment);

	}

	@Override
	protected Long invalidParentId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long privateParentId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long privateParentOtherUserId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * Test for the delete by ids version of the method
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = apiWithWriteAccess().createComment(ActivityDataUtils.ACTIVITY_WITH_COMMENTS,
					"DeleteCommentTest - please ignore"); //$NON-NLS-1$
			apiWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());
		});
	}

	@Override
	protected void validate(final StravaComment comment) throws Exception {
		StravaCommentTest.validateComment(comment);

	}

	@Override
	protected Long validParentId() {
		return ActivityDataUtils.ACTIVITY_WITH_COMMENTS;
	}
}
