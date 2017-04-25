package test.issues.strava;

import javastrava.api.API;
import javastrava.model.StravaPhoto;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue68 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final API api = new API(TestUtils.getValidTokenWithViewPrivate());

		// This one doesn't work - it's a photo uploaded via the Strava iPhone app
		final StravaPhoto[] iPhoneApp = api.listActivityPhotos(new Long(244461140L));
		if ((iPhoneApp == null) || (iPhoneApp.length == 0)) {
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
