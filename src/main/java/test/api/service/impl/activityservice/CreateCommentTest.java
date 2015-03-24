package test.api.service.impl.activityservice;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaCommentTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class CreateCommentTest extends StravaTest {
	@Test
	public void testCreateComment_valid() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final StravaComment comment = serviceWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
				StravaCommentTest.validateComment(comment);
				serviceWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());

			}
		});
	}

	@Test
	public void testCreateComment_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				StravaComment comment = null;
				try {
					comment = serviceWithWriteAccess().createComment(TestUtils.ACTIVITY_INVALID, "Test - ignore");
				} catch (final NotFoundException e) {
					// Expected behaviour
					return;
				}

				// If it got added in error, delete it again
				try {
					serviceWithWriteAccess().deleteComment(comment);
				} catch (final NotFoundException e) {
					// Ignore
				}
				fail("Added a comment to a non-existent activity");
			}
		});
	}

	@Test
	public void testCreateComment_invalidComment() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				StravaComment comment = null;
				try {
					comment = serviceWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "");
				} catch (final IllegalArgumentException e) {
					// Expected behaviour
					return;
				}

				// If it got added in error, delete it again
				try {
					serviceWithWriteAccess().deleteComment(comment);
				} catch (final NotFoundException e) {
					// Ignore
				}
				fail("Added an invalid comment to an activity");
			}
		});
	}

	@Test
	public void testCreateComment_privateActivity() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				StravaComment comment = null;
				try {
					comment = serviceWithWriteAccess().createComment(TestUtils.ACTIVITY_PRIVATE_OTHER_USER, "Test - ignore");
				} catch (final UnauthorizedException e) {
					// Expected
					return;
				}

				// If it got added in error, delete it again
				try {
					serviceWithWriteAccess().deleteComment(comment);
				} catch (final NotFoundException e) {
					// Ignore
				}
				fail("Added a comment to a private activity");
			}
		});
	}

	@Test
	public void testCreateComment_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {

				StravaComment comment = null;
				try {
					comment = service().createComment(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, "Test - ignore");
				} catch (final UnauthorizedException e) {
					// Expected
					return;
				}
				serviceWithWriteAccess().deleteComment(comment);
				fail("Created a comment despite not having write access");

			}
		});
	}

}
