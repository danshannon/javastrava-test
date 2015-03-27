package test.issues.strava;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;


/**
 * Issue test for javastrava-api #69
 * 
 * @author dshannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/69">https://github.com/danshannon/javastravav3api/issues/69</a>
 */
public class Issue69 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaActivity[] activities = new API(TestUtils.getValidToken()).listAuthenticatedAthleteActivities(null, null, 1, 200);
			for (StravaActivity activity : activities) {
				if (activity.getPrivateActivity().equals(Boolean.TRUE)) {
					return;
				}
			}
			fail("No private activities were returned, that's probably a good thing!");
		});
	}

}
