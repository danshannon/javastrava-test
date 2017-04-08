package test.issues.strava;

import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.utils.TestUtils;

/**
 * Issue test for #163 - see https://github.com/danshannon/javastravav3api/issues/163
 *
 * @author Dan Shannon
 *
 */
public class Issue163 extends IssueTest {

	/**
	 * API instance to use should have both write and view_private scope
	 */
	protected API api = new API(TestUtils.getValidTokenWithFullAccess());

	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.giveKudos(2501850L);
		} catch (final UnauthorizedException e) {
			return true;
		}
		return false;
	}

	@Override
	public int issueNumber() {
		return 163;
	}

}
