/**
 *
 */
package test.issues.strava;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Issue test for javastrava-api #73
 * </p>
 *
 * <p>
 * Tests will PASS if the issue is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/73">https://github.com/danshannon/javastravav3api/issues/73</a>
 */
public class Issue73 {
	@Test
	public void issueTest() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = new API(TestUtils.getValidToken());
			final StravaSegmentLeaderboard leaderboard = api.getSegmentLeaderboard(1190741, null, null, null, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertNotEquals(0, leaderboard.getEntries());
		});
	}
}
