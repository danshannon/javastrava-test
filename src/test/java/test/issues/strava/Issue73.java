/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.service.exception.UnauthorizedException;

/**
 * <p>
 * Issue test for javastrava-api #73
 * </p>
 *
 * <p>
 * Tests will PASS if the issue is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/73">https://github.com/danshannon/javastravav3api/issues/73</a>
 */
public class Issue73 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		try {
			api.getSegmentLeaderboard(1190741, null, null, null, null, null, null, null, null, null);
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
		return 73;
	}
}
