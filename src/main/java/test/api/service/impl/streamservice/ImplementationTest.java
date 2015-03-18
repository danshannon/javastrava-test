package test.api.service.impl.streamservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.service.StreamService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.StreamServiceImpl;

import org.junit.Test;

import test.api.service.impl.util.InstanceTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements InstanceTestSpec {

	/**
	 * Test method for {@link javastrava.api.v3.service.impl.StreamServiceImpl#instance(java.lang.String)}.
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws UnauthorizedException {
		final StreamService service = StreamServiceImpl.instance(TestUtils.getValidToken());
		assertNotNull("Didn't get a service implementation using a valid token", service);
		final List<StravaStream> streams = service.getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(streams);
	}

	@Override
	@Test
	public void testImplementation_invalidToken() {
		try {
			final StreamService service = StreamServiceImpl.instance(TestUtils.INVALID_TOKEN);
			service.getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		} catch (final UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Got a usable implementation from an invalid token");
	}

	@Override
	@Test
	public void testImplementation_implementationIsCached() {
		final StreamService service = StreamServiceImpl.instance(TestUtils.getValidToken());
		final StreamService service2 = StreamServiceImpl.instance(TestUtils.getValidToken());
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		final StreamService service = StreamServiceImpl.instance(TestUtils.getValidToken());
		final StreamService service2 = StreamServiceImpl.instance(TestUtils.getValidTokenWithoutWriteAccess());
		assertFalse(service == service2);
	}

	@Override
	@Test
	public void testImplementation_revokedToken() {
		final StreamService service = StreamServiceImpl.instance(TestUtils.getRevokedToken());
		try {
			service.getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		} catch (final UnauthorizedException e) {
			// expected
			return;
		}
		fail("Managed to use a revoked token to access streams");
	}

}