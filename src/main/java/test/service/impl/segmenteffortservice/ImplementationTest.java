package test.service.impl.segmenteffortservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.service.SegmentEffortService;
import javastrava.api.v3.service.exception.InvalidTokenException;
import javastrava.api.v3.service.impl.SegmentEffortServiceImpl;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.service.standardtests.spec.ServiceInstanceTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * Implementation tests for SegmentEffortService
 *
 * @author Dan Shannon
 *
 */
public class ImplementationTest implements ServiceInstanceTests {

	private static Token getRevokedToken() {
		return TestUtils.getRevokedToken();
	}

	private static SegmentEffortService getService() {
		return SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
	}

	private static SegmentEffortService getServiceWithoutWriteAccess() {
		return SegmentEffortServiceImpl.instance(TestUtils.getValidTokenWithWriteAccess());
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentEffortService service = getService();
			final SegmentEffortService service2 = getServiceWithoutWriteAccess();
			assertFalse(service == service2);
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentEffortService service = SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
			final SegmentEffortService service2 = SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			SegmentEffortService service = null;
			service = SegmentEffortServiceImpl.instance(TestUtils.INVALID_TOKEN);
			try {
				service.getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used an invalid token, but still got access!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentEffortService service = SegmentEffortServiceImpl.instance(getRevokedToken());
			try {
				service.getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID);
			} catch (final InvalidTokenException e) {
				// Expected
				return;
			}
			fail("Used an invalid token, still got access to Strava data!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test we get a {@link SegmentEffortServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentEffortService service = SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
			assertNotNull("Got a NULL service for a valid token", service); //$NON-NLS-1$
		});
	}
}
