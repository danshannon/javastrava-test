package test.issues.strava;

import javastrava.api.v3.service.exception.UnauthorizedException;

/**
 * @author dshannon
 *
 */
public class Issue83 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		try {
			api.listAthleteFriends(7860165, null, null);
		} catch (UnauthorizedException e) {
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
