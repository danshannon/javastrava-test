package test.issues.strava;

import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.utils.TestUtils;

/**
 * Issue test for issue #162 - see https://github.com/danshannon/javastravav3api/issues/162
 *
 * @author Dan Shannon
 *
 */
public class Issue162 extends IssueTest {

	/**
	 * API instance to use in this case should have write access but not view_private access
	 */
	protected API api = new API(TestUtils.getValidTokenWithWriteAccess());

	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.starSegment(1190741, true);
		} catch (final UnauthorizedException e) {
			return false;
		} catch (final Exception e) {
			return true;
		}
		return true;
	}

	@Override
	public int issueNumber() {
		return 162;
	}

}
