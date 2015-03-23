package test.api.service.impl.activityservice;

import javastrava.api.v3.model.StravaComment;

import org.junit.Test;

import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentTest extends StravaTest {
	@Test
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = service().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			service().deleteComment(comment.getActivityId(), comment.getId());
		});
	}

	@Test
	public void testDeleteComment_byComment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = service().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			service().deleteComment(comment);
		});
	}
}
