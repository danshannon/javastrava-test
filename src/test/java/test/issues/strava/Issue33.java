package test.issues.strava;

import javastrava.model.StravaSegmentEffort;

/**
 * <p>
 * These tests will PASS if issue 33 (<a href="https://github.com/danshannon/javastravav3api/issues/33">https://github.com/danshannon/javastravav3api/issues/33)</a> is still an issue.
 * </p>
 */
public class Issue33 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		final StravaSegmentEffort[] efforts = this.api.listSegmentEfforts(1111556, null, null, null, 1, 50);
		if (efforts.length == 0) {
			return false;
		}
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 33;
	}

}
