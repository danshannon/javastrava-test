/**
 *
 */
package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaComment;

import org.junit.Test;

import test.api.model.StravaCommentTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListAllActivityCommentsTest extends StravaTest {
	@Test
	public void testListAllActivityComments_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = service().listAllActivityComments(TestUtils.ACTIVITY_INVALID);
			assertNull(comments);
		});
	}

	@Test
	public void testListAllActivityComments_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = service().listAllActivityComments(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
			assertNotNull(comments);
			assertEquals(0, comments.size());
		});
	}

	@Test
	public void testListAllActivityComments_validActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaComment> comments = service().listAllActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS);
			assertNotNull(comments);
			assertTrue(0 < comments.size());
			for (final StravaComment comment : comments) {
				StravaCommentTest.validateComment(comment);
			}
		});
	}

}
