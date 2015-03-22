package test.api.auth.impl.retrofit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.ActivityService;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.ActivityServiceImpl;

import org.junit.BeforeClass;
import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestHttpUtils;
import test.utils.TestUtils;

public class AuthorisationServiceImplTest {
	private static TestHttpUtils HTTP_UTILITIES;

	/**
	 * <p>
	 * Loads the properties from the test configuration file
	 * </p>
	 *
	 * @throws java.lang.Exception
	 * @throws UnauthorizedException
	 *             Cannot log in successfully to Strava
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception, UnauthorizedException {
		// Set up an HTTP utility session, this will maintain a single session / session cookies etc. for you
		HTTP_UTILITIES = TestUtils.HTTP_UTILS;
		HTTP_UTILITIES.loginToSession(TestUtils.USERNAME, TestUtils.PASSWORD);
	}

	/**
	 * <p>
	 * Test getting a token with all valid settings and no scope
	 * </p>
	 *
	 * <p>
	 * Should return a token successfully which can be used to get athlete public data
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testTokenExchange_noScope() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Get a service implementation
				final AuthorisationService service = new AuthorisationServiceImpl();

				// Authorise
				final String code = HTTP_UTILITIES.approveApplication();

				// Perform the token exchange
				final Token tokenResponse = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET, code);
				assertNotNull("Token not successfully returned by Strava", tokenResponse);
			}
		});
	}

	/**
	 * <p>
	 * Test getting a token but with an invalid application identifier
	 * </p>
	 *
	 * <p>
	 * Should fail to get a token at all
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testTokenExchange_invalidClientId() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Get a service implementation
				final AuthorisationService service = new AuthorisationServiceImpl();

				// Authorise
				final String code = HTTP_UTILITIES.approveApplication();

				// Perform the token exchange
				Token tokenResponse;
				try {
					tokenResponse = service.tokenExchange(0, TestUtils.STRAVA_CLIENT_SECRET, code);
				} catch (final BadRequestException e) {
					// Expected behaviour
					return;
				}
				assertNull("Token unexpectedly returned by Strava", tokenResponse);
			}
		});
	}

	/**
	 * <p>
	 * Test getting a token but with an invalid client secret
	 * </p>
	 *
	 * <p>
	 * Should fail to get a token at all
	 * </p>
	 *
	 * @throws BadRequestException
	 * @throws Exception
	 */
	@Test
	public void testTokenExchange_invalidClientSecret() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Get a service implementation
				final AuthorisationService service = new AuthorisationServiceImpl();

				// Authorise
				final String code = HTTP_UTILITIES.approveApplication();

				// Perform the token exchange
				Token tokenResponse;
				try {
					tokenResponse = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, "", code);
				} catch (final UnauthorizedException e) {
					// Expected behaviour
					return;
				}
				assertNull("Token unexpectedly returned by Strava", tokenResponse);
			}
		});
	}

	/**
	 * <p>
	 * Test performing a token exchange with the wrong code (which is returned by Strava when access is granted)
	 * </p>
	 *
	 * <p>
	 * Should fail to get a token at all
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testTokenExchange_invalidCode() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Get a service implementation
				final AuthorisationService service = new AuthorisationServiceImpl();

				// Authorise
				@SuppressWarnings("unused")
				final String code = HTTP_UTILITIES.approveApplication();

				// Perform the token exchange
				Token tokenResponse = null;
				try {
					tokenResponse = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET, "oops wrong code!");
				} catch (final BadRequestException e) {
					// Expected behaviour
					return;
				}
				assertNull("Token unexpectedly returned by Strava", tokenResponse);
			}
		});
	}

	/**
	 * <p>
	 * Test performing a token exchange which includes request for view_private scope
	 * </p>
	 *
	 * <p>
	 * Should return a token successfully, this should grant access to private data for the authenticated athlete
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testTokenExchange_viewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Get a service implementation
				final AuthorisationService service = new AuthorisationServiceImpl();

				// Authorise
				final String code = HTTP_UTILITIES.approveApplication(AuthorisationScope.VIEW_PRIVATE);

				// Perform the token exchange
				final Token tokenResponse = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET, code,
						AuthorisationScope.VIEW_PRIVATE);
				assertNotNull("Token not successfully returned by Strava", tokenResponse);

			}
		});
	}

	/**
	 * <p>
	 * Test performing a token exchange which includes request for write access
	 * </p>
	 *
	 * <p>
	 * Should return a token successfully, this token should grant write access to the authenticated user's data
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testTokenExchange_writeScope() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Get a service implementation
				final AuthorisationService service = new AuthorisationServiceImpl();

				// Authorise
				final String code = HTTP_UTILITIES.approveApplication(AuthorisationScope.WRITE);

				// Perform the token exchange
				final Token tokenResponse = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET, code,
						AuthorisationScope.WRITE);
				assertNotNull("Token not successfully returned by Strava", tokenResponse);

				// test case to prove we've got write access
				final ActivityService activityService = ActivityServiceImpl.instance(tokenResponse);
				final StravaActivity activity = TestUtils.createDefaultActivity();
				activity.setName("AuthorisationServiceImplTest.testTokenExchange_writeScope");
				final StravaActivity response = activityService.createManualActivity(activity);
				activityService.deleteActivity(response.getId());
			}
		});
	}

	/**
	 * <p>
	 * Test performing a token exchange which includes request for both view_private and write access
	 * </p>
	 *
	 * <p>
	 * Should return a token successfully, this token should grant write access to the authenticated user
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTokenExchange_writeAndViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Get a service implementation
				final AuthorisationService service = new AuthorisationServiceImpl();

				// Authorise
				final String code = HTTP_UTILITIES.approveApplication(AuthorisationScope.VIEW_PRIVATE, AuthorisationScope.WRITE);

				// Perform the token exchange
				final Token tokenResponse = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET, code,
						AuthorisationScope.VIEW_PRIVATE, AuthorisationScope.WRITE);
				assertNotNull("Token not successfully returned by Strava", tokenResponse);
			}
		});
	}

	/**
	 * <p>
	 * Test performing a token exchange which includes request for an invalid scope
	 * </p>
	 *
	 * <p>
	 * Should not return a token successfully
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTokenExchange_invalidScope() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Get a service implementation
				final AuthorisationService service = new AuthorisationServiceImpl();

				// Authorise
				final String code = HTTP_UTILITIES.approveApplication(AuthorisationScope.UNKNOWN);

				// Perform the token exchange
				Token tokenResponse = null;
				try {
					tokenResponse = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET, code, AuthorisationScope.UNKNOWN);
				} catch (final BadRequestException e) {
					// Expected behaviour
					return;
				}
				assertNull("Token unexpectedly returned by Strava", tokenResponse);
			}
		});
	}

}
