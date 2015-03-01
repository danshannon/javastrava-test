package test.api.service.impl.retrofit.athleteservices;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import javastrava.api.v3.service.AthleteServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.AthleteServicesImpl;

import org.junit.Test;

import test.api.service.impl.ImplementationTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements ImplementationTestSpec {
	@Override
	@Test
	public void testImplementation_validToken() {
		final AthleteServices services = AthleteServicesImpl.implementation(TestUtils.getValidToken());
		assertNotNull(services);
	}

	@Override
	@Test
	public void testImplementation_invalidToken() {
		final AthleteServices services = AthleteServicesImpl.implementation(TestUtils.INVALID_TOKEN);
		try {
			services.getAuthenticatedAthlete();
		} catch (final UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Got a service object using an invalid token");
	}

	@Override
	@Test
	public void testImplementation_revokedToken() {
		final AthleteServices services = AthleteServicesImpl.implementation(TestUtils.getRevokedToken());
		try {
			services.getAuthenticatedAthlete();
		} catch (final UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Got a service object using a revoked token");

	}

	@Override
	@Test
	public void testImplementation_implementationIsCached() {
		final String token = TestUtils.getValidToken();
		final AthleteServices service1 = AthleteServicesImpl.implementation(token);
		final AthleteServices service2 = AthleteServicesImpl.implementation(token);
		assertTrue(service1 == service2);
	}

	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		final String token1 = TestUtils.getValidToken();
		final AthleteServices service1 = AthleteServicesImpl.implementation(token1);

		final String token2 = TestUtils.getValidTokenWithoutWriteAccess();
		assertFalse(token1.equals(token2));
		final AthleteServices service2 = AthleteServicesImpl.implementation(token2);
		assertFalse(service1 == service2);
	}


}
