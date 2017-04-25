package test.issues.strava;

import javastrava.service.exception.UnauthorizedException;

/**
 * @author Dan Shannon
 *
 */
public class Issue83 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.listAthleteFriends(7860165, null, null);
		} catch (final UnauthorizedException e) {
			return false;
		}
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 83;
	}

}
