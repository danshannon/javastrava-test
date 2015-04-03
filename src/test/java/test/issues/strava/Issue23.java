package test.issues.strava;

import javastrava.api.v3.service.exception.NotFoundException;

/**
 * <p>
 * These tests will PASS if issue <a href="https://github.com/danshannon/javastravav3api/issues/23">javastrava-api #23</a> is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/23">https://github.com/danshannon/javastravav3api/issues/23</a>
 */
public class Issue23 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		try {
			api.getSegmentLeaderboard(966356, null, null, null, null, 2, null, null, null, null);
		} catch (NotFoundException e) {
			return false;
		}
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 23;
	}
}
