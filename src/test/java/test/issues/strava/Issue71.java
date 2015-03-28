/**
 *
 */
package test.issues.strava;

import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * Issue test for issue javastrava-api #71
 * </p>
 *
 * <p>
 * Tests should PASS if the issue is still live
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/71">https://github.com/danshannon/javastravav3api/issues/71</a>
 */
public class Issue71 {
	@Test
	public void testIssue() {
		final API api = new API(TestUtils.getValidToken());
		final StravaSegment[] segments = api.listAuthenticatedAthleteStarredSegments(null, null);
		boolean issue = false;
		for (final StravaSegment segment : segments) {
			if (segment.getPrivateSegment().equals(Boolean.TRUE)) {
				issue = true;
			}
		}
		assertTrue(issue);
	}

}
