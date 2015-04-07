package test.api.rest.upload;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaUploadResponse;

import org.junit.Test;

import test.api.model.StravaUploadResponseTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class CheckUploadStatusTest extends APITest {
	@Test
	public void testCheckUploadStatus() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaUploadResponse response = api().checkUploadStatus(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertNotNull(response);
			StravaUploadResponseTest.validate(response);
		});
	}

	@Test
	public void testCheckUploadStatus_activityForOtherUser() throws Exception {
		// TODO Not yet implemented
		fail("Not yet implemented!");
	}

}
