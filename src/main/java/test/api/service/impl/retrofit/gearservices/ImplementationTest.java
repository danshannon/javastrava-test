package test.api.service.impl.retrofit.gearservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.service.GearServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.GearServicesImpl;

import org.junit.Test;

import test.api.service.impl.ImplementationTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements ImplementationTestSpec {
	/**
	 * <p>
	 * Test we get a {@link GearServicesImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() {
		final GearServices service = GearServicesImpl.implementation(TestUtils.getValidToken());
		assertNotNull("Got a NULL service for a valid token", service);
	}

	/**
	 * <p>
	 * Test that we don't get a {@link GearServicesImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() {
		GearServices service = null;
		try {
			service = GearServicesImpl.implementation(TestUtils.INVALID_TOKEN);
			service.getGear(TestUtils.GEAR_VALID_ID);
		} catch (final UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Have access despite having an invalid token!");
	}

	/**
	 * <p>
	 * Test that we don't get a {@link GearServicesImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() {
		final String token = getRevokedToken();
		final GearServices service = GearServicesImpl.implementation(token);

		try {
			service.getGear(TestUtils.GEAR_VALID_ID);
		} catch (final UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Have access despite having an invalid token!");
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link GearServicesImpl service implementation} for a second time, we get the SAME ONE as the
	 * first time (i.e. the caching strategy is working)
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() {
		final GearServices service = GearServicesImpl.implementation(TestUtils.getValidToken());
		final GearServices service2 = GearServicesImpl.implementation(TestUtils.getValidToken());
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link GearServicesImpl service implementation} for a second, valid, different token, we get a
	 * DIFFERENT implementation
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		final GearServices service = getGearService();
		final GearServices service2 = getGearServiceWithoutWriteAccess();
		assertFalse(service == service2);
	}

	private GearServices getGearService() {
		return GearServicesImpl.implementation(TestUtils.getValidToken());
	}

	private String getRevokedToken() {
		return TestUtils.getRevokedToken();
	}

	private GearServices getGearServiceWithoutWriteAccess() {
		return GearServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
	}


}
