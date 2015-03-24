package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.service.ActivityService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.ActivityServiceImpl;

import org.junit.Test;

import test.api.service.impl.util.InstanceTestSpec;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class ImplementationTest implements InstanceTestSpec {
	/**
	 * <p>
	 * Test we get a {@link ActivityServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final ActivityService service = service();
				assertNotNull("Got a NULL service for a valid token", service);
			}
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ActivityServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				try {
					final ActivityService service = ActivityServiceImpl.instance(TestUtils.INVALID_TOKEN);
					service.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
				} catch (final UnauthorizedException e) {
					// This is the expected behaviour
					return;
				}
				fail("Got a service for an invalid token!");
			}
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ActivityServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Attempt to get an implementation using the now invalidated token
				final ActivityService activityServices = ActivityServiceImpl.instance(TestUtils.getRevokedToken());

				// Check that it can't be used
				try {
					activityServices.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
				} catch (final UnauthorizedException e) {
					// Expected behaviour
					return;
				}

				// If we get here, then the service is working despite the token being revoked
				fail("Got a usable service implementation using a revoked token");
			}
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching
	 * strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final ActivityService service = ActivityServiceImpl.instance(TestUtils.getValidToken());
				final ActivityService service2 = ActivityServiceImpl.instance(TestUtils.getValidToken());
				assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
			}
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final Token token = TestUtils.getValidToken();
				@SuppressWarnings("unused")
				final ActivityService service = ActivityServiceImpl.instance(token);
				final Token token2 = TestUtils.getValidTokenWithWriteAccess();
				@SuppressWarnings("unused")
				final ActivityService service2 = ActivityServiceImpl.instance(token2);
				assertNotEquals("Different tokens returned the same service implementation", token, token2);
			}
		});
	}

	private ActivityService service() {
		return ActivityServiceImpl.instance(TestUtils.getValidToken());
	}

}
