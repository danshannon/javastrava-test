package test.issues.strava;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.UnauthorizedException;

/**
 * Issue test for #172 - see https://github.com/danshannon/javastravav3api/issues/172
 *
 * @author Dan Shannon
 *
 */
public class Issue172 extends IssueTest {
	/**
	 * @return <code>true</code> if the issue is still unresolved
	 */
	public static boolean issue() {
		return new Issue172().isIssue();
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() {
		final StravaComment comment;
		try {
			comment = this.api.createComment(245713183L, "Issue172.isIssue()"); //$NON-NLS-1$
		} catch (final UnauthorizedException e) {
			return false;
		} catch (final Exception e) {
			return false;
		}
		this.api.deleteComment(245713183L, comment.getId());
		return true;

	}

	@Override
	public int issueNumber() {
		return 172;
	}

}
