package test.api.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.service.ClubService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.ClubServiceImpl;

import org.junit.Test;

import test.api.service.impl.util.InstanceTestSpec;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class ImplementationTest implements InstanceTestSpec {
	/**
	 * <p>
	 * Test we get a {@link ClubServiceImpl service implementation} successfully with a valid token
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
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final ClubService service = service();
				assertNotNull("Got a NULL service for a valid token", service);
			}
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ClubServiceImpl service implementation} if the token isn't valid
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				ClubService service = null;
				try {
					service = ClubServiceImpl.instance(TestUtils.INVALID_TOKEN);
					service.getClub(TestUtils.CLUB_VALID_ID);
				} catch (final UnauthorizedException e) {
					// This is the expected behaviour
					return;
				}
				fail("Got a service for an invalid token!");
			}
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ClubServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 * 
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final ClubService service = getRevokedTokenService();
				try {
					service.getClub(TestUtils.CLUB_VALID_ID);
				} catch (final UnauthorizedException e) {
					// Expected behaviour
					return;
				}
				fail("Got a usable service implementation despite using a revoked token");
			}
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ClubServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching
	 * strategy is working)
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final ClubService service = service();
				final ClubService service2 = service();
				assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
			}
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ClubServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
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
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final ClubService service = service();
				final ClubService service2 = ClubServiceImpl.instance(TestUtils.getValidTokenWithoutWriteAccess());
				assertFalse(service == service2);
			}
		});
	}

	private ClubService service() {
		return ClubServiceImpl.instance(TestUtils.getValidToken());
	}

	private ClubService getRevokedTokenService() {
		return ClubServiceImpl.instance(TestUtils.getRevokedToken());
	}

}
