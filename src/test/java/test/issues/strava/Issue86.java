/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.service.exception.NotFoundException;

/**
 * <p>
 * Issue test for javastravav3api#86
 * </p>
 * <p>
 * Tests will PASS if this issue remains outstanding
 * </p>
 *
 * @author Dan Shannon
 * @see <a href= "https://github.com/danshannon/javastravav3api/issues/86">https://github.com/danshannon/javastravav3api/issues/86</a>
 *
 */
public class Issue86 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.listSegmentEfforts(1190741, null, null, null, null, null);
		} catch (final NotFoundException e) {
			return true;
		}
		return false;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 86;
	}

}
