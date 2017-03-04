package test.issues.strava;

import javastrava.api.v3.model.StravaSegment;

/**
 * <p>
 * These tests will PASS if issue <a href="https://github.com/danshannon/javastravav3api/issues/25">javastrava-api #25</a> is still
 * an issue
 *
 * @author Dan Shannon
 * @see <a href=
 *      "https://github.com/danshannon/javastravav3api/issues/25">https://github.com/danshannon/javastravav3api/issues/25</a>
 *
 */
public class Issue25 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		final StravaSegment[] segments = this.api.listStarredSegments(5614, 1, 2);
		for (final StravaSegment segment : segments) {
			if ((segment.getAthletePrEffort() != null) && (segment.getAthletePrEffort().getResourceState() == null)) {
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
		return 25;
	}
}
