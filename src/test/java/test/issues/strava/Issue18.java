/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaActivity;

/**
 * <p>
 * Tests for issue #18
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class Issue18 extends IssueTest {

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
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		final StravaActivity[] both = this.api.listFriendsActivities(1, 2);
		final StravaActivity[] first = this.api.listFriendsActivities(1, 1);
		final StravaActivity[] second = this.api.listFriendsActivities(2, 1);

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
		return 18;
	}

}
