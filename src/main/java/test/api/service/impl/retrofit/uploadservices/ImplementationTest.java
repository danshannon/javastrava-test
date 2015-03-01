package test.api.service.impl.retrofit.uploadservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaUploadResponse;
import javastrava.api.v3.service.UploadServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.UploadServicesImpl;

import org.junit.Test;

import test.api.service.impl.ImplementationTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements ImplementationTestSpec {

	/**
	 * Test method for {@link javastrava.api.v3.service.impl.retrofit.StreamServicesImpl#implementation(java.lang.String)}.
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws UnauthorizedException {
		final UploadServices service = UploadServicesImpl.implementation(TestUtils.getValidToken());
		assertNotNull("Didn't get a service implementation using a valid token", service);
		final StravaUploadResponse response = service.checkUploadStatus(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(response);
	}

	@Override
	@Test
	public void testImplementation_invalidToken() {
		try {
			final UploadServices service = UploadServicesImpl.implementation(TestUtils.INVALID_TOKEN);
			service.checkUploadStatus(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		} catch (final UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Got a usable implementation from an invalid token");
	}

	@Override
	@Test
	public void testImplementation_revokedToken() {
		try {
			final UploadServices service = UploadServicesImpl.implementation(TestUtils.getRevokedToken());
			service.checkUploadStatus(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		} catch (final UnauthorizedException e) {
			// Expected
			return;
		}
		fail("Got a service implementation with a valid token!");
	}

	@Override
	@Test
	public void testImplementation_implementationIsCached() {
		final String token = TestUtils.getValidToken();
		final UploadServices service = UploadServicesImpl.implementation(token);
		final UploadServices service2 = UploadServicesImpl.implementation(token);
		assertEquals(service, service2);
	}

	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		final UploadServices service = UploadServicesImpl.implementation(TestUtils.getValidToken());
		final UploadServices service2 = UploadServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
		assertFalse(service == service2);
	}


}
