package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import test.api.rest.TestDeleteCallback;
import test.api.rest.activity.DeleteCommentTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentAsyncTest extends DeleteCommentTest {
	public DeleteCommentAsyncTest() {
		super();

		this.callback = new TestDeleteCallback<StravaComment, Integer>() {
			@Override
			public StravaComment run(final API api, final StravaComment comment, final Integer id) throws Exception {
				api.deleteCommentAsync(id, comment.getId()).get();
				return null;
			}
		};
	}

	@Override
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = apiWithWriteAccess().createCommentAsync(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore").get();
			apiWithWriteAccess().deleteCommentAsync(comment.getActivityId(), comment.getId()).get();
		});
	}

}
