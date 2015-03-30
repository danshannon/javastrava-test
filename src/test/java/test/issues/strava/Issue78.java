package test.issues.strava;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;


/**
 * <p>
 * Issue test for issue javastrava-api #78
 * </p>
 * 
 * <p>
 * Tests will PASS if this is still an ongoing issue
 * </p>
 * 
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/78">https://github.com/danshannon/javastravav3api/issues/78</a>
 */
public class Issue78 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = new API(TestUtils.getValidToken());
			StravaSegmentEffort effort = api.getSegmentEffort(120026887L);
			assertNotNull(effort);
		});
	}
}
