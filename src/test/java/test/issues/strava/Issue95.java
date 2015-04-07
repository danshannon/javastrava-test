package test.issues.strava;

import javastrava.api.v3.service.exception.UnauthorizedException;

/**
 * @author Dan Shannon
 *
 */
public class Issue95 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.listActivityKudoers(244461140, null, null);
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
		return 95;
	}

}
