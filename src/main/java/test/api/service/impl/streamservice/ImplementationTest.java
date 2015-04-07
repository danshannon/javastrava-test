package test.api.service.impl.streamservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.service.StreamService;
import javastrava.api.v3.service.exception.InvalidTokenException;
import javastrava.api.v3.service.impl.StreamServiceImpl;

import org.junit.Test;

import test.api.service.impl.util.InstanceTestSpec;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ImplementationTest implements InstanceTestSpec {

	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = StreamServiceImpl.instance(TestUtils.getValidToken());
			final StreamService service2 = StreamServiceImpl.instance(TestUtils.getValidTokenWithWriteAccess());
			assertFalse(service == service2);
		});
	}

	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = StreamServiceImpl.instance(TestUtils.getValidToken());
			final StreamService service2 = StreamServiceImpl.instance(TestUtils.getValidToken());
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
		});
	}

	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = StreamServiceImpl.instance(TestUtils.INVALID_TOKEN);
			try {
				service.getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used an invalid token but still got access to Strava!");
		});
	}

	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = StreamServiceImpl.instance(TestUtils.getRevokedToken());
			try {
				service.getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used a revoked token but still got access to Strava!");
		});
	}

	/**
	 * Test method for {@link javastrava.api.v3.service.impl.StreamServiceImpl#instance(java.lang.String)}.
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = StreamServiceImpl.instance(TestUtils.getValidToken());
			assertNotNull("Didn't get a service implementation using a valid token", service);
			final List<StravaStream> streams = service.getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertNotNull(streams);
		});
	}

}
