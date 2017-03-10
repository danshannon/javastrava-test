package test.issues.strava;

import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

/**
 * <p>
 * Issue test for issue javastrava-api #45
 * </p>
 * <p>
 * Tests will PASS if the issue is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href= "https://github.com/danshannon/javastravav3api/issues/45">https://github.com/danshannon/javastravav3api/issues/45</a>
 */
public class Issue45 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		try {
			this.api.listSegmentEfforts(8857183, null, null, null, null, null);
		} catch (final NotFoundException e) {
			return true;
		} catch (final UnauthorizedException e) {
			return false;
		}
		// Shouldn't be able to get here; if we do it's returning data, but shouldn't
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 45;
	}
}
