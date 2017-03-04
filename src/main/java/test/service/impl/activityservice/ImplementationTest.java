package test.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.service.ActivityService;
import javastrava.api.v3.service.exception.InvalidTokenException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.ActivityServiceImpl;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.spec.ServiceInstanceTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Service implementation tests
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ImplementationTest implements ServiceInstanceTests {
	@SuppressWarnings("static-method")
	private ActivityService service() {
		return ActivityServiceImpl.instance(TestUtils.getValidToken());
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServiceImpl service implementation} for a second, valid, different token, we get a
	 * DIFFERENT implementation
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Token token = TestUtils.getValidToken();
			ActivityServiceImpl.instance(token);
			final Token token2 = TestUtils.getValidTokenWithWriteAccess();
			ActivityServiceImpl.instance(token2);
			assertNotEquals("Different tokens returned the same service implementation", token, token2); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServiceImpl service implementation} for a second time, we get the SAME ONE as the
	 * first time (i.e. the caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ActivityService service = ActivityServiceImpl.instance(TestUtils.getValidToken());
			final ActivityService service2 = ActivityServiceImpl.instance(TestUtils.getValidToken());
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ActivityServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ActivityService service = ActivityServiceImpl.instance(TestUtils.INVALID_TOKEN);
			try {
				service.getActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used an invalid token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ActivityServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Attempt to get an implementation using the now invalidated token
			final ActivityService activityServices = ActivityServiceImpl.instance(TestUtils.getRevokedToken());

			// Check that it can't be used
			try {
				activityServices.getActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used a revoked token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test we get a {@link ActivityServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ActivityService service = service();
			assertNotNull("Got a NULL service for a valid token", service); //$NON-NLS-1$
		});
	}

}
