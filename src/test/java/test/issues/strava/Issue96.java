/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaActivity;
import javastrava.config.StravaConfig;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue96 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final StravaActivity[] activities = this.api.listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE);
		for (final StravaActivity activity : activities) {
			if (activity.getAthlete().getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID) && activity.getPrivateActivity()) {
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
		return 96;
	}

}
