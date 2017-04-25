package test.issues.strava;

import javastrava.service.exception.UnauthorizedException;

/**
 * Issue test for #161
 *
 *
 * @author Dan Shannon
 *
 */
public class Issue161 extends IssueTest {

	/**
	 * @return <code>true</code> if the issue is still unresolved
	 */
	public static boolean issue() {
		try {
			return new Issue161().isIssue();
		} catch (final Exception e) {
			return false;
		}
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.listActivityKudoers(865756452L, null, null);
		} catch (final UnauthorizedException e) {
			return false;
		} catch (final Exception e) {
			return true;
		}
		return true;
	}

	@Override
	public int issueNumber() {
		return 161;
	}

}
