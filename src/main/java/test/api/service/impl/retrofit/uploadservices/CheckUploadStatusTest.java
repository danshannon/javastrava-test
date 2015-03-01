package test.api.service.impl.retrofit.uploadservices;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaUploadResponse;
import javastrava.api.v3.service.UploadServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.UploadServicesImpl;

import org.junit.Test;

import test.api.model.StravaUploadResponseTest;
import test.utils.TestUtils;

public class CheckUploadStatusTest {
	@Test
	public void testCheckUploadStatus() throws UnauthorizedException {
		final UploadServices service = getService();
		final StravaUploadResponse response = service.checkUploadStatus(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(response);
		StravaUploadResponseTest.validate(response);
	}

	private UploadServices getService() {
		return UploadServicesImpl.implementation(TestUtils.getValidToken());
	}

}
