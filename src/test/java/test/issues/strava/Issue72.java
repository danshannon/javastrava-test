/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.JavastravaApplicationConfig;
import test.api.rest.APITest;
import test.utils.TestUtils;

/**
 * <p>
 * Issue test for issue javastrava-api #72
 * </p>
 *
 * <p>
 * Test should PASS if the issue is still current
 * </p>
 *
 * @author Dan Shannon
 * @see <a href= "https://github.com/danshannon/javastravav3api/issues/72">https://github.com/danshannon/javastravav3api/issues/72</a>
 *
 */
public class Issue72 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		// Can't do the test unless we have delete access
		if (!JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE) {
			throw new IllegalStateException("Can't run test because Strava won't allow activity delete"); //$NON-NLS-1$
		}

		// Create a private activity
		final StravaActivity activity = APITest.createPrivateActivity("Issue72.testIssue()"); //$NON-NLS-1$

		// 2 instances of the API: one with and one without view_private
		final API apiWithWriteAccess = new API(TestUtils.getValidTokenWithWriteAccess());

		// Attempt to update the activity
		final StravaActivityUpdate activityUpdate = new StravaActivityUpdate();
		activityUpdate.setDescription("Test issue 72"); //$NON-NLS-1$

		// This should throw a 401 Unauthorised, but if the issue is current it won't have
		try {
			apiWithWriteAccess.updateActivity(activity.getId(), activityUpdate);
		} catch (final UnauthorizedException e) {
			APITest.forceDeleteActivity(activity.getId());
			return false;
		}
		APITest.forceDeleteActivity(activity.getId());
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 72;
	}
}
