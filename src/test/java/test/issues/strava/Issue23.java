package test.issues.strava;

import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.SegmentAPI;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if issue <a href="https://github.com/danshannon/javastravav3api/issues/23">javastrava-api #23</a> is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/23">https://github.com/danshannon/javastravav3api/issues/23</a>
 */
public class Issue23 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentAPI retrofit = API.instance(SegmentAPI.class, TestUtils.getValidToken());
			retrofit.getSegmentLeaderboard(966356, null, null, null, null, 2, null, null, null, null);
			// Note - that's all for this test, because it should have thrown a NotFoundException

			});

	}
}
