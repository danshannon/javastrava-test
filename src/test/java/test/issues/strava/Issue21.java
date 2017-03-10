package test.issues.strava;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamType;

/**
 * <p>
 * These tests should PASS if issue <a href="https://github.com/danshannon/javastravav3api/issues/21">javastrava-api #21</a> is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href= "https://github.com/danshannon/javastravav3api/issues/21">https://github.com/danshannon/javastravav3api/issues/21</a>
 */
public class Issue21 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		final StravaStream[] streams = this.api.getActivityStreams(245713183L, StravaStreamType.DISTANCE.toString(), null, null);
		for (final StravaStream stream : streams) {
			if (stream.getResolution() != null) {
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
		return 21;
	}
}
