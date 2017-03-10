/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaSegment;

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
 * @see <a href= "https://github.com/danshannon/javastravav3api/issues/71">https://github.com/danshannon/javastravav3api/issues/71</a>
 */
public class Issue71 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final StravaSegment[] segments = this.api.listAuthenticatedAthleteStarredSegments(null, null);
		for (final StravaSegment segment : segments) {
			if (segment.getPrivateSegment().equals(Boolean.TRUE)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 71;
	}

}
