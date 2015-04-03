/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaPhoto;
import test.utils.TestUtils;

/**
 * <p>
 * Issue test for issue #76
 * </p>
 *
 * <p>
 * Tests should PASS if the issue is still a problem
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/76">https://github.com/danshannon/javastravav3api/issues/76</a>
 */
public class Issue76 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final StravaPhoto[] photos = api.listActivityPhotos(TestUtils.ACTIVITY_WITHOUT_PHOTOS);
		if (photos == null) {
			return true;
		}
		return false;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 76;
	}
}
