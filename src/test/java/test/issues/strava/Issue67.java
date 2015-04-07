/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.APITest;

/**
 * <p>
 * Issue test for issue javastrava-api #67 - tests will PASS if the issue is still a problem
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/67>https://github.com/danshannon/javastravav3api/issues/67</a>
 */
public class Issue67 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final StravaComment comment = APITest.createPrivateActivityWithComment("Issue67.testIssue()");
		try {
			api.listActivityComments(comment.getActivityId(), null, null, null);
		} catch (UnauthorizedException e) {
			APITest.forceDeleteActivity(comment.getActivityId());
			return false;
		}
		APITest.forceDeleteActivity(comment.getActivityId());
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 67;
	}
}
