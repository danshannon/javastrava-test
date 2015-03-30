package test.api.service.impl.segmenteffortservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.SegmentEffortService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.SegmentEffortServiceImpl;

import org.junit.Test;

import test.api.service.impl.util.InstanceTestSpec;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ImplementationTest implements InstanceTestSpec {

	private Token getRevokedToken() {
		return TestUtils.getRevokedToken();
	}

	private SegmentEffortService getService() {
		return SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
	}

	private SegmentEffortService getServiceWithoutWriteAccess() {
		return SegmentEffortServiceImpl.instance(TestUtils.getValidTokenWithWriteAccess());
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT
	 * implementation
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
		RateLimitedTestRunner.run(() -> {
			final SegmentEffortService service = getService();
			final SegmentEffortService service2 = getServiceWithoutWriteAccess();
			assertFalse(service == service2);
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the
	 * caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentEffortService service = SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
			final SegmentEffortService service2 = SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			SegmentEffortService service = null;
			service = SegmentEffortServiceImpl.instance(TestUtils.INVALID_TOKEN);
			final StravaSegmentEffort effort = service.getSegmentEffort(TestUtils.SEGMENT_EFFORT_VALID_ID);
			assertEquals(StravaResourceState.PRIVATE, effort.getResourceState());

		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentEffortService service = SegmentEffortServiceImpl.instance(getRevokedToken());
			final StravaSegmentEffort effort = service.getSegmentEffort(TestUtils.SEGMENT_EFFORT_VALID_ID);
			assertEquals(StravaResourceState.PRIVATE, effort.getResourceState());

		});
	}

	/**
	 * <p>
	 * Test we get a {@link SegmentEffortServiceImpl service implementation} successfully with a valid token
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
		RateLimitedTestRunner.run(() -> {
			final SegmentEffortService service = SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
			assertNotNull("Got a NULL service for a valid token", service);
		});
	}
}
