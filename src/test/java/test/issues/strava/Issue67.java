/**
 *
 */
package test.issues.strava;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.ActivityAPI;

import org.junit.Test;

import test.api.service.impl.util.ActivityServiceUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Issue test for issue javastrava-api #67 - tests will PASS if the issue is still a problem
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/67>https://github.com/danshannon/javastravav3api/issues/67</a>
 */
public class Issue67 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ActivityAPI api = API.instance(ActivityAPI.class, TestUtils.getValidToken());
			final StravaComment comment = ActivityServiceUtils.createPrivateActivityWithComment("Issue67.testIssue()");
			final List<StravaComment> comments = Arrays.asList(api.listActivityComments(comment.getActivityId(), null, null, null));
			assertNotNull(comments);
			assertFalse(comments.isEmpty());
		});
	}
}
