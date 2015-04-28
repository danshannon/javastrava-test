package test.api.rest.activity;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.BadRequestException;
import test.api.model.StravaCommentTest;
import test.api.rest.APICreateTest;
import test.issues.strava.Issue30;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class CreateCommentTest extends APICreateTest<StravaComment, Integer> {
	public CreateCommentTest() {
		super();
		this.creationCallback = (api, objectToCreate, id) -> api.createComment(id, objectToCreate.getText());
	}

	@Override
	public void create_validParentNoWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#30
			if (new Issue30().isIssue()) {
				return;
			}
			// End of workaround

			super.create_validParentNoWriteAccess();

		} );
	}

	/**
	 * @see test.api.rest.APICreateTest#createObject()
	 */
	@Override
	protected StravaComment createObject() {
		final StravaComment comment = new StravaComment();
		comment.setText("Test - ignore!");
		return comment;
	}

	/**
	 * @see test.api.rest.APICreateTest#forceDelete(java.lang.Object)
	 */
	@Override
	protected void forceDelete(final StravaComment comment) {
		forceDeleteComment(comment);
	}

	/**
	 * @see test.api.rest.APICreateTest#invalidParentId()
	 */
	@Override
	protected Integer invalidParentId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APICreateTest#privateParentId()
	 */
	@Override
	protected Integer privateParentId() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.rest.APICreateTest#privateParentOtherUserId()
	 */
	@Override
	protected Integer privateParentOtherUserId() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Test
	public void testCreateComment_invalidComment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			StravaComment comment = new StravaComment();
			comment.setText("");
			try {
				comment = this.creationCallback.run(api, comment, TestUtils.ACTIVITY_WITH_COMMENTS);
			} catch (final BadRequestException e1) {
				// Expected behaviour
				return;
			}

			// If it got added in error, delete it again
			forceDeleteComment(comment);
			fail("Added an invalid comment to an activity");
		} );
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaComment comment) throws Exception {
		StravaCommentTest.validateComment(comment);

	}

	/**
	 * @see test.api.rest.APICreateTest#validParentId()
	 */
	@Override
	protected Integer validParentId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APICreateTest#validParentOtherUserId()
	 */
	@Override
	protected Integer validParentOtherUserId() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

}
