package test.api.service.impl.retrofit.activityservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.service.ActivityServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesImpl;

import org.junit.Test;

import test.api.service.impl.ImplementationTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements ImplementationTestSpec {
	/**
	 * <p>
	 * Test we get a {@link ActivityServicesImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() {
		final ActivityServices service = service();
		assertNotNull("Got a NULL service for a valid token", service);
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ActivityServicesImpl service implementation} if the token isn't valid
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() {
		try {
			final ActivityServices service = ActivityServicesImpl.implementation(TestUtils.INVALID_TOKEN);
			service.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		} catch (final UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Got a service for an invalid token!");
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ActivityServicesImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() {
		// Attempt to get an implementation using the now invalidated token
		final ActivityServices activityServices = ActivityServicesImpl.implementation(TestUtils.getRevokedToken());

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

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServicesImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the
	 * caching strategy is working)
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() {
		final ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		final ActivityServices service2 = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServicesImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		final String token = TestUtils.getValidToken();
		@SuppressWarnings("unused")
		final
		ActivityServices service = ActivityServicesImpl.implementation(token);
		final String token2 = TestUtils.getValidTokenWithoutWriteAccess();
		@SuppressWarnings("unused")
		final
		ActivityServices service2 = ActivityServicesImpl.implementation(token2);
		assertNotEquals("Different tokens returned the same service implementation", token, token2);
	}

	private ActivityServices service() {
		return ActivityServicesImpl.implementation(TestUtils.getValidToken());
	}


}
