package test.issues.strava;

import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue82 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		try {
			API api = new API(TestUtils.getValidTokenWithWriteAccess());
			api.giveKudos(113235);
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
		return 82;
	}

}
