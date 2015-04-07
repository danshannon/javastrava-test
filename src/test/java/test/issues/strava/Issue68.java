package test.issues.strava;

import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue68 extends IssueTest {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {

		});
	}

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		API api = new API(TestUtils.getValidTokenWithViewPrivate());

		// This one doesn't work - it's a photo uploaded via the Strava iPhone app
		StravaPhoto[] iPhoneApp = api.listActivityPhotos(244461140);
		if (iPhoneApp == null || iPhoneApp.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 68;
	}
}
