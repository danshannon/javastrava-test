package test.issues.strava;

import javastrava.model.StravaActivity;

/**
 * Issue test for #96 - see https://github.com/danshannon/javastravav3api/issues/96
 *
 * @author Dan Shannon
 *
 */
public class Issue96 extends IssueTest {

	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		final StravaActivity[] activities = this.api.listFriendsActivities(1, 200);
		for (final StravaActivity activity : activities) {
			if (activity.getPrivateActivity()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int issueNumber() {
		return 96;
	}

}
