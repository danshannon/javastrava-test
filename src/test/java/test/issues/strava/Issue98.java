package test.issues.strava;

import javastrava.service.exception.UnauthorizedException;
import test.service.standardtests.data.AthleteDataUtils;

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
			this.api.listStarredSegments(AthleteDataUtils.ATHLETE_PRIVATE_ID, null, null);
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
