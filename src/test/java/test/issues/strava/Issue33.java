package test.issues.strava;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.SegmentAPI;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if issue 33 (<a
 * href="https://github.com/danshannon/javastravav3api/issues/33">https://github.com/danshannon/javastravav3api/issues/33)</a> is still an issue.
 * </p>
 */
public class Issue33 {
	/**
	 * This test will PASS if issue 33 (https://github.com/danshannon/javastravav3api/issues/33) is still an issue
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIssue33() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Create a retrofit service to use
				final SegmentAPI retrofit = API.instance(SegmentAPI.class, TestUtils.getValidToken());
				final List<StravaSegmentEffort> efforts = Arrays.asList(retrofit.listSegmentEfforts(1111556, null, null, null, null, null));
				assertNotNull(efforts);
				assertFalse(efforts.isEmpty());
			});
	}

}
