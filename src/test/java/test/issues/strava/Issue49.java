package test.issues.strava;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.StravaInternalServerErrorException;
import test.api.rest.APITest;
import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if issue javastrava-api #49 is still an issue
 * </p>
 *
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/49">https://github.com/danshannon/javastravav3api/issues/49</a>
 * @author Dan Shannon
 *
 */
public class Issue49 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final API api = new API(TestUtils.getValidTokenWithWriteAccess());
		StravaActivity activity = TestUtils.createDefaultActivity("Issue49.testIssue");
		activity.setType(StravaActivityType.UNKNOWN);
		try {
			activity = api.createManualActivity(activity);
		} catch (final StravaInternalServerErrorException e) {
			return true;
		} catch (final BadRequestException e) {
			return false;
		}
		APITest.forceDeleteActivity(activity);
		return false;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 49;
	}
}
