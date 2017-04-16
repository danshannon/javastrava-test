package test.issues.strava;

import javastrava.api.v3.service.exception.UnauthorizedException;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * These tests will PASS if <a href="https://github.com/danshannon/javastravav3api/issues/29">javastrava-api #29</a> remains an issue
 * </p>
 *
 * @author Dan Shannon
 * @see <a href= "https://github.com/danshannon/javastravav3api/issues/29">https://github.com/danshannon/javastravav3api/issues/29</a>
 *
 */
public class Issue29 extends IssueTest {
	/**
	 * <code>true</code> if issue is still unresolved
	 */
	public static boolean issue = Issue29.issue();

	/**
	 * @return <code>true</code> if issue is still unresolved
	 */
	public static boolean issue() {
		try {
			return new Issue29().isIssue();
		} catch (final Exception e) {
			return false;
		}
	}

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.giveKudos(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
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
		return 29;
	}

}
