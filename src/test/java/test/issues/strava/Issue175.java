package test.issues.strava;

import test.service.standardtests.data.AthleteDataUtils;

/**
 * Issue test for issue 175, code attempting to compensate for physical laziness
 *
 * @author Dan Shannon
 *
 */
public class Issue175 extends IssueTest {
	/**
	 * @return <code>true</code> if the authenticated athlete is too slack to have enough KOM's
	 */
	public static boolean issue() {
		try {
			return new Issue175().isIssue();
		} catch (final Exception e) {
			return false;
		}
	}

	@Override
	public boolean isIssue() throws Exception {
		final int koms = this.api.listAthleteKOMs(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, null, null).length;
		if (koms < 2) {
			return true;
		}
		return false;
	}

	@Override
	public int issueNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

}
