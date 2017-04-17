package test.issues.strava;

import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.StravaAPIRateLimitException;
import test.utils.TestUtils;

/**
 * Issue test for #164 - see https://github.com/danshannon/javastravav3api/issues/164
 *
 * @author Dan Shannon
 *
 */
public class Issue164 extends IssueTest {
	/**
	 * <code>true</code> if the issue is still a problem
	 */
	public static final boolean issue = issue();

	/**
	 * @return <code>true</code> if the issue is still a problem
	 */
	private static boolean issue() {
		try {
			return new Issue164().isIssue();
		} catch (final Exception e) {
			return false;
		}
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api = new API(TestUtils.getValidTokenWithFullAccess());
			this.api.joinClub(123582);
			this.api.joinClub(123579);
		} catch (final StravaAPIRateLimitException e) {
			// Expected
			return true;
		} catch (final Exception e) {
			return false;
		}
		return false;
	}

	@Override
	public int issueNumber() {
		return 164;
	}

	@Override
	public boolean isIntermittent() {
		return true;
	}

}
