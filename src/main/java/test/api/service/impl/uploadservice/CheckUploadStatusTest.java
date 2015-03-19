package test.api.service.impl.uploadservice;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaUploadResponse;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaUploadResponseTest;
import test.api.service.StravaTest;
import test.utils.TestUtils;

public class CheckUploadStatusTest extends StravaTest {
	@Test
	public void testCheckUploadStatus() throws UnauthorizedException {
		final StravaUploadResponse response = service().checkUploadStatus(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(response);
		StravaUploadResponseTest.validate(response);
	}

}
