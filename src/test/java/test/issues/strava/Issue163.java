package test.issues.strava;

import javastrava.api.API;
import javastrava.service.exception.UnauthorizedException;
import test.utils.TestUtils;

/**
 * Issue test for #163 - see https://github.com/danshannon/javastravav3api/issues/163
 *
 * @author Dan Shannon
 *
 */
public class Issue163 extends IssueTest {
	/**
	 * <code>true</code> if issue is still unresolved
	 */
	public static final boolean issue = issue();

	private static boolean issue() {
		return new Issue163().isIssue();
	}

	/**
	 * API instance to use should have both write and view_private scope
	 */
	protected API api = new API(TestUtils.getValidTokenWithFullAccess());

	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() {
		try {
			this.api.giveKudos(2501850L);
		} catch (final UnauthorizedException e) {
			return true;
		} catch (final Exception e) {
			return false;
		}
		return false;
	}

	@Override
	public int issueNumber() {
		return 163;
	}

}
