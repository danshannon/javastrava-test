package test.issues.strava;

import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * Issue test for javastrava-api #70
 *
 * @author Dan Shannon
 *
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/70">https://github.com/danshannon/javastravav3api/issues/70</a>
 */
public class Issue70 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = new API(TestUtils.getValidToken());
			api.getSegment(TestUtils.SEGMENT_PRIVATE_ID);
			// This should NOT get here (API should throw a 401 Unauthorised), but it does, that's the test passed!
		});
	}
}
