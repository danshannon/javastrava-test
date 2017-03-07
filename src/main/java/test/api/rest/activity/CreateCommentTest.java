package test.api.rest.activity;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.config.JavastravaApplicationConfig;
import test.api.model.StravaCommentTest;
import test.api.rest.APICreateTest;
import test.api.rest.callback.TestCreateCallback;
import test.issues.strava.Issue30;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.CommentDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * API tests for {@link API#createComment(Long, String)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class CreateCommentTest extends APICreateTest<StravaComment, Long> {
	@Override
	protected TestCreateCallback<StravaComment, Long> creator() {
		return ((api, object, id) -> api.createComment(id, object.getText()));
	}

	@Override
	public void create_invalidParent() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			super.create_invalidParent();
		}
	}

	@Override
	public void create_privateParentBelongsToOtherUser() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			super.create_privateParentBelongsToOtherUser();
		}
	}

	@Override
	public void create_privateParentWithoutViewPrivate() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			super.create_privateParentWithoutViewPrivate();
		}
	}

	@Override
	public void create_privateParentWithViewPrivate() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			super.create_privateParentWithViewPrivate();
		}
	}

	@Override
	public void create_valid() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			super.create_valid();
		}
	}

	@Override
	public void create_validParentBelongsToOtherUser() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			super.create_validParentBelongsToOtherUser();
		}
	}

	@Override
	public void create_validParentNoWriteAccess() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			RateLimitedTestRunner.run(() -> {
				if (new Issue30().isIssue()) {
					return;
				}

				super.create_validParentNoWriteAccess();

			});
		}
	}

	@Override
	protected StravaComment createObject() {
		return CommentDataUtils.generateValidObject();
	}

	@Override
	protected void forceDelete(final StravaComment comment) {
		forceDeleteComment(comment);
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
	 * Attempt to create an empty comment. Create call should fail with a {@link BadRequestException}.
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testCreateComment_invalidComment() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_COMMENTS_WRITE) {
			RateLimitedTestRunner.run(() -> {
				final API api = apiWithWriteAccess();
				StravaComment comment = new StravaComment();
				comment.setText(""); //$NON-NLS-1$
				try {
					comment = creator().create(api, comment, ActivityDataUtils.ACTIVITY_WITH_COMMENTS);
				} catch (final BadRequestException e1) {
					// Expected behaviour
					return;
				}

				// If it got added in error, delete it again
				forceDeleteComment(comment);
				fail("Added an invalid comment to an activity"); //$NON-NLS-1$
			});
		}
	}

	@Override
	protected void validate(final StravaComment comment) throws Exception {
		StravaCommentTest.validateComment(comment);

	}

	@Override
	protected Long validParentId() {
		return ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	@Override
	protected Long validParentOtherUserId() {
		return ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

}
