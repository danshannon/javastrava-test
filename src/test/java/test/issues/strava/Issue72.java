/**
 *
 */
package test.issues.strava;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Issue test for issue javastrava-api #72
 * </p>
 *
 * <p>
 * Test should PASS if the issue is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/72">https://github.com/danshannon/javastravav3api/issues/72</a>
 *
 */
public class Issue72 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.createDefaultActivity("Issue72.testIssue()");
			activity.setPrivateActivity(Boolean.TRUE);
			final API apiWithFullAccess = new API(TestUtils.getValidTokenWithFullAccess());
			final StravaActivity response = apiWithFullAccess.createManualActivity(activity);
			final API apiWithWriteAccess = new API(TestUtils.getValidTokenWithWriteAccess());
			final StravaActivityUpdate activityUpdate = new StravaActivityUpdate();
			activityUpdate.setDescription("Test");
			// This should throw a 401 Unauthorised, but if the issue is current it won't have
				try {
					apiWithWriteAccess.updateActivity(response.getId(), activityUpdate);
				} catch (final UnauthorizedException e) {
					apiWithFullAccess.deleteActivity(response.getId());
					fail("Appears to be fixed");
				}
				apiWithFullAccess.deleteActivity(response.getId());
			});
	}
}
