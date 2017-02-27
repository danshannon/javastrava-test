package test.api.service.impl.streamservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.service.StreamService;
import javastrava.api.v3.service.exception.InvalidTokenException;
import javastrava.api.v3.service.impl.StreamServiceImpl;
import test.api.service.standardtests.spec.ServiceInstanceTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * StreamService implementation tests
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ImplementationTest implements ServiceInstanceTests {

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
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = StreamServiceImpl.instance(TestUtils.INVALID_TOKEN);
			try {
				service.getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used an invalid token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = StreamServiceImpl.instance(TestUtils.getRevokedToken());
			try {
				service.getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used a revoked token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = StreamServiceImpl.instance(TestUtils.getValidToken());
			assertNotNull("Didn't get a service implementation using a valid token", service); //$NON-NLS-1$
			final List<StravaStream> streams = service.getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertNotNull(streams);
		});
	}

}
