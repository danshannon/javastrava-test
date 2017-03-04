package test.issues.strava;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.APITest;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * These tests will PASS if <a href="https://github.com/danshannon/javastravav3api/issues/30">issue javastrava-api #30</a> remains
 * an issue
 * </p>
 *
 * @author Dan Shannon
 * @see <a href=
 *      "https://github.com/danshannon/javastravav3api/issues/30">https://github.com/danshannon/javastravav3api/issues/30</a>
 */
public class Issue30 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		StravaComment comment = null;
		try {
			comment = this.api.createComment(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, "Test - ignore"); //$NON-NLS-1$
		} catch (final UnauthorizedException e) {
			return false;
		}

		// Force delete the comment we just created
		APITest.forceDeleteComment(comment);
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 30;
	}

}
