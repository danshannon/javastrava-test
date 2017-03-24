package test.service.impl.athleteservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.service.AthleteService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.AthleteServiceImpl;
import test.service.standardtests.spec.ServiceInstanceTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Tests for {@link AthleteServiceImpl} instances
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
			final Token token1 = TestUtils.getValidToken();
			final AthleteService service1 = AthleteServiceImpl.instance(token1);

			final Token token2 = TestUtils.getValidTokenWithWriteAccess();
			assertFalse(token1.equals(token2));
			final AthleteService service2 = AthleteServiceImpl.instance(token2);
			assertFalse(service1 == service2);
		});
	}

	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Token token = TestUtils.getValidToken();
			final AthleteService service1 = AthleteServiceImpl.instance(token);
			final AthleteService service2 = AthleteServiceImpl.instance(token);
			assertTrue(service1 == service2);
		});
	}

	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final AthleteService services = AthleteServiceImpl.instance(TestUtils.INVALID_TOKEN);
			try {
				services.getAuthenticatedAthlete();
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Got a service object using an invalid token"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final AthleteService services = AthleteServiceImpl.instance(TestUtils.getRevokedToken());
			try {
				services.getAuthenticatedAthlete();
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Got a service object using a revoked token"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final AthleteService services = AthleteServiceImpl.instance(TestUtils.getValidToken());
			assertNotNull(services);
		});
	}

}
