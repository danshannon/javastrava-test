/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaActivity;

/**
 * <p>
 * Issue test for issue javastravav3api#84
 * </p>
 * <p>
 * Tests will PASS if this remains outstanding
 * </p>
 * 
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/84">https://github.com/danshannon/javastravav3api/issues/84</a>
 */
public class Issue84 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final StravaActivity activity = this.api.getActivity(277585159, null);
		if (activity.getAverageCadence() != null) {
			return true;
		}
		return false;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 84;
	}

}
