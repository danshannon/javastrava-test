package test.issues.strava;

import javastrava.api.v3.service.exception.UnauthorizedException;

/**
 * Issue test for #161
 *
 *
 * @author Dan Shannon
 *
 */
public class Issue161 extends IssueTest {

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
