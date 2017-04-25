/**
 *
 */
package test.issues.strava;

import javastrava.model.StravaLap;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Tests for issue #105
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class Issue105 extends IssueTest {
	/**
	 * @return <code>true</code> if the issue is still unresolved
	 */
	public static boolean issue() {
		try {
			return new Issue105().isIssue();
		} catch (final Exception e) {
			return false;
		}
	}

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final StravaLap[] laps = this.api.listActivityLaps(ActivityDataUtils.ACTIVITY_WITHOUT_LAPS);
		if (laps.length == 0) {
			return false;
		}
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 105;
	}

}
