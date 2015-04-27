package test.api.rest.activity;

import java.util.List;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.api.model.StravaCommentTest;
import test.api.rest.APIDeleteTest;
import test.api.rest.APITest;
import test.api.rest.TestDeleteCallback;
import test.issues.strava.Issue63;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentTest extends APIDeleteTest<StravaComment, Integer> {
	public DeleteCommentTest() {
		super();

		this.callback = new TestDeleteCallback<StravaComment, Integer>() {
			@Override
			public StravaComment run(final API api, final StravaComment comment, final Integer id) throws Exception {
				api.deleteComment(id, comment.getId());
				return null;
			}
		};
	}

	@Test
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = apiWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			apiWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());
		});
	}

	/**
	 * @see test.api.rest.APIDeleteTest#createObject()
	 */
	@Override
	protected StravaComment createObject() {
		return apiWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
	}

	/**
	 * @see test.api.rest.APIDeleteTest#invalidParentId()
	 */
	@Override
	protected Integer invalidParentId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIDeleteTest#privateParentId()
	 */
	@Override
	protected Integer privateParentId() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.rest.APIDeleteTest#validParentId()
	 */
	@Override
	protected Integer validParentId() {
		return TestUtils.ACTIVITY_WITH_COMMENTS;
	}

	/**
	 * @see test.api.rest.APIDeleteTest#privateParentOtherUserId()
	 */
	@Override
	protected Integer privateParentOtherUserId() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.api.rest.APIDeleteTest#forceDelete(java.lang.Object)
	 */
	@Override
	protected void forceDelete(final StravaComment comment) {
		APITest.forceDeleteComment(comment);

	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaComment comment) throws Exception {
		StravaCommentTest.validateComment(comment);

	}

	/**
	 * @see test.api.rest.APITest#validateList(java.util.List)
	 */
	@Override
	protected void validateList(final List<StravaComment> comments) throws Exception {
		StravaCommentTest.validateCommentList(comments);
	}

	/**
	 * @see test.api.rest.APIDeleteTest#delete_validParentNoWriteAccess()
	 */
	@Override
	public void delete_validParentNoWriteAccess() throws Exception {
		if (new Issue63().isIssue()) {
			return;
		}
		super.delete_validParentNoWriteAccess();
	}
}
