/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaLap;
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
