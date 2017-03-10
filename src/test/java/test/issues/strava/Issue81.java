package test.issues.strava;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.rest.API;
import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if issue <a href="https://github.com/danshannon/javastravav3api/issues/81">javastrava-api #25</a> is still an issue
 *
 * @author Dan Shannon
 * @see <a href= "https://github.com/danshannon/javastravav3api/issues/81">https://github.com/danshannon/javastravav3api/issues/81</a>
 *
 */
public class Issue81 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		final API api = new API(TestUtils.getValidToken());
		final StravaSegment[] segments = api.listAuthenticatedAthleteStarredSegments(1, 50);
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
		return 81;
	}
}
