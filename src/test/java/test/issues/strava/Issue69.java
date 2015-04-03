package test.issues.strava;

import javastrava.api.v3.model.StravaActivity;


/**
 * Issue test for javastrava-api #69
 * 
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/69">https://github.com/danshannon/javastravav3api/issues/69</a>
 */
public class Issue69 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		StravaActivity[] activities = api.listAuthenticatedAthleteActivities(null, null, 1, 200);
		for (StravaActivity activity : activities) {
			if (activity.getPrivateActivity().equals(Boolean.TRUE)) {
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
		return 69;
	}

}
