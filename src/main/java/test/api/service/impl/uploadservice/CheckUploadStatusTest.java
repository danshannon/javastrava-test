package test.api.service.impl.uploadservice;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaUploadResponse;

import org.junit.Test;

import test.api.model.StravaUploadResponseTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class CheckUploadStatusTest extends StravaTest {
	@Test
	public void testCheckUploadStatus() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final StravaUploadResponse response = strava().checkUploadStatus(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
				assertNotNull(response);
				StravaUploadResponseTest.validate(response);
			}
		});
	}

}
