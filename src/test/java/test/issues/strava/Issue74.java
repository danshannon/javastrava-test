/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.APITest;
import test.utils.TestUtils;

/**
 * <p>
 * Issue test for issue javastava-api #74
 * </p>
 * <p>
 * Tests will PASS if the issue is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/74">https://github.com/danshannon/javastravav3api/issues/74</a>
 */
public class Issue74 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		StravaComment comment = APITest.createPrivateActivityWithComment("Issue74.isIssue()");
		API api = new API(TestUtils.getValidTokenWithWriteAccess());
		try {
			api.deleteComment(comment.getActivityId(), comment.getId());
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
		return 74;
	}
}
