package test.api.service.impl.gearservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.service.GearService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.GearServiceImpl;

import org.junit.Test;

import test.api.service.impl.util.InstanceTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements InstanceTestSpec {
	/**
	 * <p>
	 * Test we get a {@link GearServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() {
		final GearService service = GearServiceImpl.instance(TestUtils.getValidToken());
		assertNotNull("Got a NULL service for a valid token", service);
	}

	/**
	 * <p>
	 * Test that we don't get a {@link GearServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() {
		GearService service = null;
		try {
			service = GearServiceImpl.instance(TestUtils.INVALID_TOKEN);
			service.getGear(TestUtils.GEAR_VALID_ID);
		} catch (final UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Have access despite having an invalid token!");
	}

	/**
	 * <p>
	 * Test that we don't get a {@link GearServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() {
		final Token token = getRevokedToken();
		final GearService service = GearServiceImpl.instance(token);

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
	 * Test that when we ask for a {@link GearServiceImpl service implementation} for a second time, we get the SAME ONE as the
	 * first time (i.e. the caching strategy is working)
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() {
		final GearService service = GearServiceImpl.instance(TestUtils.getValidToken());
		final GearService service2 = GearServiceImpl.instance(TestUtils.getValidToken());
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link GearServiceImpl service implementation} for a second, valid, different token, we get a
	 * DIFFERENT implementation
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		final GearService service = getGearService();
		final GearService service2 = getGearServiceWithoutWriteAccess();
		assertFalse(service == service2);
	}

	private GearService getGearService() {
		return GearServiceImpl.instance(TestUtils.getValidToken());
	}

	private Token getRevokedToken() {
		return TestUtils.getRevokedToken();
	}

	private GearService getGearServiceWithoutWriteAccess() {
		return GearServiceImpl.instance(TestUtils.getValidTokenWithoutWriteAccess());
	}


}
