package test.api.service.impl.activityservice;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.UnauthorizedException;

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
			final StravaComment comment = serviceWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			service().deleteComment(comment.getActivityId(), comment.getId());
		});
	}

	@Test
	public void testDeleteComment_byComment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = serviceWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			service().deleteComment(comment);
		});
	}

	@Test
	public void testDeleteComment_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = serviceWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			try {
				service().deleteComment(comment);
			} catch (final UnauthorizedException e) {
				// Expected - delete the comment anyway
				serviceWithWriteAccess().deleteComment(comment);
				return;
			}
			serviceWithWriteAccess().deleteComment(comment);
			fail("Deleted a comment using a token without write access");
		});
	}
}
