/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.service.exception.BadRequestException;

/**
 * @author Dan Shannon
 *
 */
public class Issue91 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.getEffortStreams(5614162014L, "UNKNOWN", null, null); //$NON-NLS-1$
		} catch (final BadRequestException e) {
			return false;
		}
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 91;
	}

}
