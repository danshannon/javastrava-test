package test.api.service.impl.segmenteffortservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.service.SegmentEffortService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.SegmentEffortServiceImpl;

import org.junit.Test;

import test.api.service.impl.util.InstanceTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements InstanceTestSpec {

	/**
	 * <p>
	 * Test we get a {@link SegmentEffortServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() {
		final SegmentEffortService service = SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
		assertNotNull("Got a NULL service for a valid token", service);
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServiceImpl service implementation} if the token isn't valid
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() {
		SegmentEffortService service = null;
		try {
			service = SegmentEffortServiceImpl.instance(TestUtils.INVALID_TOKEN);
			service.getSegmentEffort(TestUtils.SEGMENT_EFFORT_VALID_ID);
		} catch (final UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Got a working service for an invalid token!");
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() {
		try {
			final SegmentEffortService service = SegmentEffortServiceImpl.instance(getRevokedToken());
			service.getSegmentEffort(TestUtils.SEGMENT_EFFORT_VALID_ID);
		} catch (final UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Revoked a token, but it's still useful");
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the
	 * caching strategy is working)
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() {
		final SegmentEffortService service = SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
		final SegmentEffortService service2 = SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT
	 * implementation
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		final SegmentEffortService service = getService();
		final SegmentEffortService service2 = getServiceWithoutWriteAccess();
		assertFalse(service == service2);
	}

	private SegmentEffortService getService() {
		return SegmentEffortServiceImpl.instance(TestUtils.getValidToken());
	}

	private Token getRevokedToken() {
		return TestUtils.getRevokedToken();
	}

	private SegmentEffortService getServiceWithoutWriteAccess() {
		return SegmentEffortServiceImpl.instance(TestUtils.getValidTokenWithoutWriteAccess());
	}
}
