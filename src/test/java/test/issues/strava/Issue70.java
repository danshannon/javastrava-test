package test.issues.strava;

import javastrava.service.exception.UnauthorizedException;

/**
 * Issue test for javastrava-api #70
 *
 * @author Dan Shannon
 *
 * @see <a href= "https://github.com/danshannon/javastravav3api/issues/70">https://github.com/danshannon/javastravav3api/issues/70</a>
 */
public class Issue70 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.getSegment(1190741);
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
		return 70;
	}
}
