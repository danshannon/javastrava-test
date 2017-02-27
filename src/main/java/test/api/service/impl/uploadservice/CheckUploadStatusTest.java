package test.api.service.impl.uploadservice;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaUploadResponse;
import javastrava.api.v3.service.Strava;
import test.api.model.StravaUploadResponseTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#checkUploadStatus(Long)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class CheckUploadStatusTest {
	/**
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testCheckUploadStatus() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaUploadResponse response = TestUtils.strava().checkUploadStatus(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertNotNull(response);
			StravaUploadResponseTest.validate(response);
		});
	}

}
