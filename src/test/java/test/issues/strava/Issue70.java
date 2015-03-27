package test.issues.strava;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * Issue test for javastrava-api #70
 * 
 * @author dshannon
 *
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/70">https://github.com/danshannon/javastravav3api/issues/70</a>
 */
public class Issue70 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
			API api = new API(TestUtils.getValidToken());
			StravaSegment segment = api.getSegment(TestUtils.SEGMENT_PRIVATE_ID);
			// This should NOT get here, but it does, that's the test passed!
		});
	}
}
