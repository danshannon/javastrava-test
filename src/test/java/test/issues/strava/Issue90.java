/**
 *
 */
package test.issues.strava;

import javastrava.service.exception.BadRequestException;

/**
 * @author Dan Shannon
 *
 */
public class Issue90 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.getSegmentStreams(612630, "UNKNOWN", null, null); //$NON-NLS-1$
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
		return 90;
	}

}
