/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.StravaInternalServerErrorException;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue111 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		try {
			final API api = new API(TestUtils.getValidToken());
			api.getAthlete(0);
		} catch (final StravaInternalServerErrorException e) {
			return true;
		}
		return false;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 111;
	}

}
