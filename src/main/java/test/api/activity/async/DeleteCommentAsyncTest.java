package test.api.activity.async;

import javastrava.model.StravaComment;
import test.api.activity.DeleteCommentTest;
import test.api.callback.APIDeleteCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentAsyncTest extends DeleteCommentTest {
	@Override
	protected String classUnderTest() {
		return this.getClass().getName();
	}

	@Override
	public APIDeleteCallback<StravaComment> deleter() {
		return ((api, comment) -> {
			api.deleteCommentAsync(comment.getActivityId(), comment.getId()).get();
			return comment;
		});
	}

	@Override
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = apiWithWriteAccess().createComment(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, "DeleteCommentAsyncTest - please ignore"); //$NON-NLS-1$
			apiWithWriteAccess().deleteCommentAsync(comment.getActivityId(), comment.getId()).get();
		});
	}

}
