/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Issue test for issue javastava-api #74
 * </p>
 * <p>
 * Tests will PASS if the issue is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/74">https://github.com/danshannon/javastravav3api/issues/74</a>
 */
public class Issue74 {
	@Test
	public void issueTest() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.createDefaultActivity("Issue74.issueTest()");
			final API api = new API(TestUtils.getValidTokenWithFullAccess());
			final StravaActivity response = api.createManualActivity(activity);
			final API apiWithWriteAccess = new API(TestUtils.getValidTokenWithWriteAccess());
			final StravaComment comment = apiWithWriteAccess.createComment(response.getId(), "Test - ignore");
			apiWithWriteAccess.deleteComment(response.getId(), comment.getId());
			api.deleteActivity(response.getId());
		});
	}
}
