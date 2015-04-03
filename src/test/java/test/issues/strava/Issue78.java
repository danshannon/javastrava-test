package test.issues.strava;

import javastrava.api.v3.service.exception.UnauthorizedException;


/**
 * <p>
 * Issue test for issue javastrava-api #78
 * </p>
 * 
 * <p>
 * Tests will PASS if this is still an ongoing issue
 * </p>
 * 
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/78">https://github.com/danshannon/javastravav3api/issues/78</a>
 */
public class Issue78 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		try {
			api.getSegmentEffort(120026887L);
		} catch (UnauthorizedException e) {
			return false;
		} 
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 78;
	}
}
