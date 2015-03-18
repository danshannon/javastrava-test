package test.api.service.impl.uploadservice;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaUploadResponse;
import javastrava.api.v3.service.UploadService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.UploadServiceImpl;

import org.junit.Test;

import test.api.model.StravaUploadResponseTest;
import test.utils.TestUtils;

public class CheckUploadStatusTest {
	@Test
	public void testCheckUploadStatus() throws UnauthorizedException {
		final UploadService service = getService();
		final StravaUploadResponse response = service.checkUploadStatus(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(response);
		StravaUploadResponseTest.validate(response);
	}

	private UploadService getService() {
		return UploadServiceImpl.instance(TestUtils.getValidToken());
	}

}
