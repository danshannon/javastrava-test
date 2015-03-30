package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.ActivityService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.ActivityServiceImpl;

import org.junit.Test;

import test.api.service.impl.util.InstanceTestSpec;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ImplementationTest implements InstanceTestSpec {
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
			assertNotEquals("Different tokens returned the same service implementation", token, token2);
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServiceImpl service implementation} for a second time, we get the SAME ONE as the
	 * first time (i.e. the caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ActivityService service = ActivityServiceImpl.instance(TestUtils.getValidToken());
			final ActivityService service2 = ActivityServiceImpl.instance(TestUtils.getValidToken());
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
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
		RateLimitedTestRunner.run(() -> {
			final ActivityService service = ActivityServiceImpl.instance(TestUtils.INVALID_TOKEN);
			StravaActivity activity = service.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertEquals(StravaResourceState.PRIVATE, activity.getResourceState());
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
		RateLimitedTestRunner.run(() -> {
			// Attempt to get an implementation using the now invalidated token
				final ActivityService activityServices = ActivityServiceImpl.instance(TestUtils.getRevokedToken());

				// Check that it can't be used
				final StravaActivity activity = activityServices.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
				assertTrue(activity.getResourceState() == StravaResourceState.PRIVATE);

			});
	}

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
		RateLimitedTestRunner.run(() -> {
			final ActivityService service = service();
			assertNotNull("Got a NULL service for a valid token", service);
		});
	}

}
