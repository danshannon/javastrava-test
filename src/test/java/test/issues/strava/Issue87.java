package test.issues.strava;

import javastrava.api.v3.service.exception.UnauthorizedException;

/**
 * <p>
 * Issue test for javastravav3api#87
 * </p>
 * <p>
 * Tests will PASS if the issue remains outstanding
 * </p>
 *
 * @author Dan Shannon
 * @see <a href= "https://github.com/danshannon/javastravav3api/issues/87">https://github.com/danshannon/javastravav3api/issues/87</a>
 */
public class Issue87 extends IssueTest {

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
		return 87;
	}

}
