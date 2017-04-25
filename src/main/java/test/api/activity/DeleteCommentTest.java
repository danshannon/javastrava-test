package test.api.activity;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import org.junit.Test;

import javastrava.api.API;
import javastrava.config.JavastravaApplicationConfig;
import javastrava.model.StravaComment;
import javastrava.service.exception.NotFoundException;
import test.api.APIDeleteTest;
import test.api.APITest;
import test.api.callback.APIDeleteCallback;
import test.issues.strava.Issue63;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.CommentDataUtils;
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
	protected String classUnderTest() {
		return this.getClass().getName();
	}

	@Override
	protected StravaComment createObject(String name) {
		return apiWithWriteAccess().createComment(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, name + " - test data only - please ignore"); //$NON-NLS-1$
	}

	@Override
	protected StravaComment createPrivateObject(String name) {
		// No such thing!
		return null;
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void delete_invalidParent() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE);

		try {
			apiWithFullAccess().deleteComment(ActivityDataUtils.ACTIVITY_INVALID, 0);
		} catch (final NotFoundException e) {
			// Expected
			return;
		}
		fail("Attempt to delete a non-existent comment with id = 0 appears to have worked successfully!"); //$NON-NLS-1$

	}

	@Override
	@Test
	public void delete_privateParentBelongsToOtherUser() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE);

		// There's no way to create data anyway
		return;
	}

	@Override
	@Test
	public void delete_privateParentWithoutViewPrivate() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE);

		super.delete_privateParentWithoutViewPrivate();
	}

	@Override
	@Test
	public void delete_privateParentWithViewPrivate() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE);

		super.delete_privateParentWithViewPrivate();
	}

	@Override
	@Test
	public void delete_valid() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			super.delete_valid();
		}
	}

	@Override
	@Test
	public void delete_validParentNoWriteAccess() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE);

		assumeFalse(new Issue63().isIssue());

		super.delete_validParentNoWriteAccess();
	}

	@Override
	public APIDeleteCallback<StravaComment> deleter() {
		return ((api, comment) -> {
			api.deleteComment(comment.getActivityId(), comment.getId());
			return comment;
		});
	}

	@Override
	protected boolean deleteReturnsNull() {
		return false;
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
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			RateLimitedTestRunner.run(() -> {
				final StravaComment comment = apiWithWriteAccess().createComment(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, "DeleteCommentTest - please ignore"); //$NON-NLS-1$
				apiWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());
			});
		}
	}

	@Override
	protected void validate(final StravaComment comment) throws Exception {
		CommentDataUtils.validateComment(comment);

	}

	@Override
	protected Long validParentId() {
		return ActivityDataUtils.ACTIVITY_WITH_COMMENTS;
	}
}
