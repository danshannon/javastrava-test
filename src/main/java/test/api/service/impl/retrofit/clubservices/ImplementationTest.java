package test.api.service.impl.retrofit.clubservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.service.ClubServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.ClubServicesImpl;

import org.junit.Test;

import test.api.service.impl.ImplementationTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements ImplementationTestSpec{
	/**
	 * <p>
	 * Test we get a {@link ClubServicesImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() {
		final ClubServices service = service();
		assertNotNull("Got a NULL service for a valid token", service);
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ClubServicesImpl service implementation} if the token isn't valid
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() {
		ClubServices service = null;
		try {
			service = ClubServicesImpl.implementation(TestUtils.INVALID_TOKEN);
			service.getClub(TestUtils.CLUB_VALID_ID);
		} catch (final UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Got a service for an invalid token!");
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ClubServicesImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() {
		final ClubServices service = getRevokedTokenService();
		try {
			service.getClub(TestUtils.CLUB_VALID_ID);
		} catch (final UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Got a usable service implementation despite using a revoked token");
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ClubServicesImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching
	 * strategy is working)
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() {
		final ClubServices service = service();
		final ClubServices service2 = service();
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ClubServicesImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		final ClubServices service = service();
		final ClubServices service2 = ClubServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
		assertFalse(service == service2);
	}

	private ClubServices service() {
		return ClubServicesImpl.implementation(TestUtils.getValidToken());
	}

	private ClubServices getRevokedTokenService() {
		return ClubServicesImpl.implementation(TestUtils.getRevokedToken());
	}


}
