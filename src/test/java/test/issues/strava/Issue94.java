package test.issues.strava;

import javastrava.api.v3.model.StravaActivity;

/**
 * @author Dan Shannon
 *
 */
public class Issue94 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIntermittent()
	 */
	@Override
	public boolean isIntermittent() {
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final StravaActivity[] both = this.api.listRecentClubActivities(2130, 1, 2);
		final StravaActivity[] first = this.api.listRecentClubActivities(2130, 1, 1);
		final StravaActivity[] second = this.api.listRecentClubActivities(2130, 2, 1);

		if (both[0].equals(first[0]) && both[1].equals(second[0])) {
			return false;
		}
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 94;
	}

}
