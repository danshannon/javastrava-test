package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.service.SegmentService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.SegmentServiceImpl;

import org.junit.Test;

import test.api.service.impl.util.InstanceTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements InstanceTestSpec {

	/**
	 * <p>
	 * Test we get a {@link SegmentServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws UnauthorizedException {
		final SegmentService service = SegmentServiceImpl.instance(TestUtils.getValidToken());
		assertNotNull("Got a NULL service for a valid token", service);
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentServiceImpl service implementation} if the token isn't valid
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() {
		try {
			final SegmentService service = SegmentServiceImpl.instance(TestUtils.INVALID_TOKEN);
			service.getSegment(TestUtils.SEGMENT_VALID_ID);
		} catch (final UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Got a working service for an invalid token!");
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws UnauthorizedException {
		final SegmentService service = SegmentServiceImpl.instance(TestUtils.getRevokedToken());
		try {
			service.getSegment(TestUtils.SEGMENT_VALID_ID);
		} catch (final UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Got a working service for a revoked token!");
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching
	 * strategy is working)
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws UnauthorizedException {
		final SegmentService service = SegmentServiceImpl.instance(TestUtils.getValidToken());
		final SegmentService service2 = SegmentServiceImpl.instance(TestUtils.getValidToken());
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws UnauthorizedException {
		final SegmentService service = SegmentServiceImpl.instance(TestUtils.getValidToken());
		final SegmentService service2 = SegmentServiceImpl.instance(TestUtils.getValidTokenWithoutWriteAccess());
		assertFalse(service == service2);
	}


}