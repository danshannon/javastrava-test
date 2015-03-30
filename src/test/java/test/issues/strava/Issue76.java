/**
 *
 */
package test.issues.strava;

import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.rest.API;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Issue test for issue #76
 * </p>
 *
 * <p>
 * Tests should PASS if the issue is still a problem
 * </p>
 *
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/76">https://github.com/danshannon/javastravav3api/issues/76</a>
 */
public class Issue76 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = new API(TestUtils.getValidToken());
			final StravaPhoto[] photos = api.listActivityPhotos(TestUtils.ACTIVITY_WITHOUT_PHOTOS);
			assertNull(photos);
		});
	}
}
