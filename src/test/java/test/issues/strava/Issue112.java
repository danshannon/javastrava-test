/**
 *
 */
package test.issues.strava;

import javastrava.api.API;
import javastrava.service.exception.UnauthorizedException;
import test.utils.TestUtils;

/**
 * <p>
 * Tests for issue #112
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class Issue112 extends IssueTest {
	/**
	 * @return <code>true</code> if the issue is still unresolved
	 */
	public static boolean issue() {
		try {
			return new Issue112().isIssue();
		} catch (final Exception e) {
			return false;
		}
	}

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			final API api = new API(TestUtils.getValidToken());
			api.listClubGroupEvents(124418);
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
		return 112;
	}

}
