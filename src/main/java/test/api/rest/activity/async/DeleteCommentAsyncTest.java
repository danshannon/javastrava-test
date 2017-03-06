package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaComment;
import test.api.rest.activity.DeleteCommentTest;
import test.api.rest.callback.TestDeleteCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentAsyncTest extends DeleteCommentTest {

	@Override
	public TestDeleteCallback<StravaComment, Long> deleter() {
		return ((api, comment, id) -> {
			api.deleteCommentAsync(id, comment.getId()).get();
			return comment;
		});
	}

	@Override
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = apiWithWriteAccess().createComment(ActivityDataUtils.ACTIVITY_WITH_COMMENTS,
					"DeleteCommentAsyncTest - please ignore"); //$NON-NLS-1$
			apiWithWriteAccess().deleteCommentAsync(comment.getActivityId(), comment.getId()).get();
		});
	}

}
