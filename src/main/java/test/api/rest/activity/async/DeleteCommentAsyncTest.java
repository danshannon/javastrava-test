package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaComment;
import test.api.rest.activity.DeleteCommentTest;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentAsyncTest extends DeleteCommentTest {
	public DeleteCommentAsyncTest() {
		super();

		this.callback = (api, comment, id) -> {
			api.deleteCommentAsync(id, comment.getId()).get();
			return null;
		};
	}

	@Override
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = apiWithWriteAccess().createCommentAsync(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore")
					.get();
			apiWithWriteAccess().deleteCommentAsync(comment.getActivityId(), comment.getId()).get();
		});
	}

}
