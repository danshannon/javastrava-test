package test.issues.strava;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue68 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {

			API api = new API(TestUtils.getValidTokenWithViewPrivate());

			// This one should work - it's a photo uploaded to Instagram
			StravaPhoto[] instagram = api.listActivityPhotos(245713183);

			// This one doesn't work - it's a photo uploaded via the Strava iPhone app
			StravaPhoto[] iPhoneApp = api.listActivityPhotos(244461140);
			assertNotNull(instagram);
			assertFalse(instagram.length == 0);
			assertNull(iPhoneApp);
		});
	}
}
