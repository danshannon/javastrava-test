package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.SegmentService;
import javastrava.api.v3.service.impl.SegmentServiceImpl;

import org.junit.Test;

import test.api.service.impl.util.InstanceTestSpec;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ImplementationTest implements InstanceTestSpec {

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentServiceImpl service implementation} for a second, valid, different token, we get a
	 * DIFFERENT implementation
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentService service = SegmentServiceImpl.instance(TestUtils.getValidToken());
			final SegmentService service2 = SegmentServiceImpl.instance(TestUtils.getValidTokenWithWriteAccess());
			assertFalse(service == service2);
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentServiceImpl service implementation} for a second time, we get the SAME ONE as the
	 * first time (i.e. the caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentService service = SegmentServiceImpl.instance(TestUtils.getValidToken());
			final SegmentService service2 = SegmentServiceImpl.instance(TestUtils.getValidToken());
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentService service = SegmentServiceImpl.instance(TestUtils.INVALID_TOKEN);
			StravaSegment segment = service.getSegment(TestUtils.SEGMENT_VALID_ID);
			assertEquals(StravaResourceState.PRIVATE, segment.getResourceState());
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentService service = SegmentServiceImpl.instance(TestUtils.getRevokedToken());
			StravaSegment segment = service.getSegment(TestUtils.SEGMENT_VALID_ID);
			assertEquals(StravaResourceState.PRIVATE, segment.getResourceState());
		});
	}

	/**
	 * <p>
	 * Test we get a {@link SegmentServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentService service = SegmentServiceImpl.instance(TestUtils.getValidToken());
			assertNotNull("Got a NULL service for a valid token", service);
		});
	}

}
