package test.api.rest.upload;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaUploadResponse;
import test.api.model.StravaUploadResponseTest;
import test.api.rest.APIGetTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class CheckUploadStatusTest extends APIGetTest<StravaUploadResponse, Integer> {
	@Test
	public void testCheckUploadStatus() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaUploadResponse response = api().checkUploadStatus(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertNotNull(response);
			StravaUploadResponseTest.validate(response);
		} );
	}

	@Test
	public void testCheckUploadStatus_activityForOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			api().checkUploadStatus(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
		} );
	}

	@Test
	public void testCheckUploadStatus_privateActivityForOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			api().checkUploadStatus(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
		} );
	}

}
