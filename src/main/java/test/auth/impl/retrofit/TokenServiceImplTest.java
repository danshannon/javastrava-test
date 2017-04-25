package test.auth.impl.retrofit;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.auth.model.Token;
import javastrava.service.Strava;
import javastrava.service.exception.UnauthorizedException;
import javastrava.service.impl.AthleteServiceImpl;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Tests for the token service
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class TokenServiceImplTest {
	/**
	 * <p>
	 * Test behaviour when a token is deauthorised TWICE
	 * </p>
	 *
	 * <p>
	 * Should fail the second time
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDeauthorise_deauthorisedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// 1. Get a deauthorised token
			final Token token = TestUtils.getValidToken();

			// 2. Attempt to deauthorise it twice
			final Strava service = new Strava(token);
			service.deauthorise(token);
			try {
				service.deauthorise(token);
			} catch (final UnauthorizedException e) {
				// This is the expected behaviour
			}
		});
	}

	/**
	 * <p>
	 * Test deauthorisation of an invalid (i.e. non-existent) token
	 * </p>
	 *
	 * <p>
	 * Should fail
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDeauthorise_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// 1. Get a valid token
			Token token = TestUtils.getValidToken();

			// 2. Get a service implementation for the valid token
			final Strava service = new Strava(token);

			// 3. Get an INVALID token
			token = TestUtils.INVALID_TOKEN;

			// 4. Attempt to deauthorise the invalid token
			try {
				service.deauthorise(token);
			} catch (final UnauthorizedException e) {
				// This is the expected behaviour
			}
		});
	}

	/**
	 * <p>
	 * Test deauthorisation of a valid token
	 * </p>
	 *
	 * <p>
	 * Should succeed; token should no longer be able to be used for access
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDeauthorise_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// 2. Attempt to use the token to get a service implementation
			try {
				AthleteServiceImpl.instance(TestUtils.getRevokedToken()).getAuthenticatedAthlete();
			} catch (final UnauthorizedException e) {
				// This is expected behaviour
				return;
			}

			// 3. We should NOT get a service implementation
			fail("Got a usable service implementation despite successfully deauthorising the token"); //$NON-NLS-1$
		});
	}
}
