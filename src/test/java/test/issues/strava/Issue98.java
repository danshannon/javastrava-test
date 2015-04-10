package test.issues.strava;

import javastrava.api.v3.service.exception.UnauthorizedException;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue98 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.listStarredSegments(TestUtils.ATHLETE_PRIVATE_ID, null, null);
		} catch (final UnauthorizedException e) {
			// expected
			return false;
		}
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 98;
	}

}
