package test.issues.strava;

import static org.junit.Assert.fail;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.SegmentAPI;
import javastrava.api.v3.service.exception.NotFoundException;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Issue test for issue javastrava-api #45
 * </p>
 * <p>
 * Tests will PASS if the issue is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/45">https://github.com/danshannon/javastravav3api/issues/45</a>
 */
public class Issue45 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentAPI retrofit = API.instance(SegmentAPI.class, TestUtils.getValidToken());
			try {
				retrofit.listSegmentEfforts(8857183, null, null, null, null, null);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Issue not behaving as expected");
		});
	}
}
