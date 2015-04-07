package test.issues.strava;

import javastrava.api.v3.service.exception.UnauthorizedException;
import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if <a href="https://github.com/danshannon/javastravav3api/issues/29">javastrava-api #29</a> remains an issue
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/29">https://github.com/danshannon/javastravav3api/issues/29</a>
 *
 */
public class Issue29 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		try {
			api.giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
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
		return 29;
	}

}
