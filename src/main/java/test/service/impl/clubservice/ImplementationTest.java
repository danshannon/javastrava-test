package test.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.service.ClubService;
import javastrava.api.v3.service.exception.InvalidTokenException;
import javastrava.api.v3.service.impl.ClubServiceImpl;
import test.service.standardtests.data.ClubDataUtils;
import test.service.standardtests.spec.ServiceInstanceTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * Service implementation tests for {@link ClubService}
 *
 * @author Dan Shannon
 *
 */
public class ImplementationTest implements ServiceInstanceTests {
	private static ClubService getRevokedTokenService() {
		return ClubServiceImpl.instance(TestUtils.getRevokedToken());
	}

	private static ClubService service() {
		return ClubServiceImpl.instance(TestUtils.getValidToken());
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ClubServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ClubService service = service();
			final ClubService service2 = ClubServiceImpl.instance(TestUtils.getValidTokenWithWriteAccess());
			assertFalse(service == service2);
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ClubServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ClubService service = service();
			final ClubService service2 = service();
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ClubServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ClubService service = ClubServiceImpl.instance(TestUtils.INVALID_TOKEN);
			try {
				service.getClub(ClubDataUtils.CLUB_VALID_ID);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used an invalid token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ClubServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ClubService service = getRevokedTokenService();
			try {
				service.getClub(ClubDataUtils.CLUB_VALID_ID);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used a revoked token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test we get a {@link ClubServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ClubService service = service();
			assertNotNull("Got a NULL service for a valid token", service); //$NON-NLS-1$
		});
	}

}
