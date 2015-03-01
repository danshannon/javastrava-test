package test.api.service.impl.retrofit.segmentservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.service.SegmentServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;

import org.junit.Test;

import test.api.service.impl.ImplementationTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements ImplementationTestSpec {

	/**
	 * <p>
	 * Test we get a {@link SegmentServicesImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws UnauthorizedException {
		final SegmentServices service = SegmentServicesImpl.implementation(TestUtils.getValidToken());
		assertNotNull("Got a NULL service for a valid token", service);
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentServicesImpl service implementation} if the token isn't valid
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() {
		try {
			final SegmentServices service = SegmentServicesImpl.implementation(TestUtils.INVALID_TOKEN);
			service.getSegment(TestUtils.SEGMENT_VALID_ID);
		} catch (final UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Got a working service for an invalid token!");
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentServicesImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws UnauthorizedException {
		final SegmentServices service = SegmentServicesImpl.implementation(TestUtils.getRevokedToken());
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
	 * Test that when we ask for a {@link SegmentServicesImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching
	 * strategy is working)
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws UnauthorizedException {
		final SegmentServices service = SegmentServicesImpl.implementation(TestUtils.getValidToken());
		final SegmentServices service2 = SegmentServicesImpl.implementation(TestUtils.getValidToken());
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentServicesImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws UnauthorizedException {
		final SegmentServices service = SegmentServicesImpl.implementation(TestUtils.getValidToken());
		final SegmentServices service2 = SegmentServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
		assertFalse(service == service2);
	}


}
