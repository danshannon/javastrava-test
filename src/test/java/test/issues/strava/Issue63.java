package test.issues.strava;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.APITest;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Issue test for issue javastrava-api #63
 * </p>
 *
 * <p>
 * Tests will PASS if the issue is still a problem
 * </p>
 *
 * @author Dan Shannon
 * @see <a href=
 *      "https://github.com/danshannon/javastravav3api/issues/63">https://github.com/danshannon/javastravav3api/issues/63</a>
 */
public class Issue63 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final StravaComment comment = APITest.forceCreateComment(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, "Test - ignore"); //$NON-NLS-1$
		try {
			this.api.deleteComment(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, comment.getId());
		} catch (final UnauthorizedException e) {
			APITest.forceDeleteComment(comment);
			return false;
		}
		APITest.forceDeleteComment(comment);
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
}
