package test.api.service.impl.retrofit.segmenteffortservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.service.SegmentEffortServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.SegmentEffortServicesImpl;

import org.junit.Test;

import test.api.service.impl.ImplementationTestSpec;
import test.utils.TestUtils;

public class ImplementationTest implements ImplementationTestSpec {

	/**
	 * <p>
	 * Test we get a {@link SegmentEffortServicesImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() {
		final SegmentEffortServices service = SegmentEffortServicesImpl.implementation(TestUtils.getValidToken());
		assertNotNull("Got a NULL service for a valid token", service);
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServicesImpl service implementation} if the token isn't valid
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() {
		SegmentEffortServices service = null;
		try {
			service = SegmentEffortServicesImpl.implementation(TestUtils.INVALID_TOKEN);
			service.getSegmentEffort(TestUtils.SEGMENT_EFFORT_VALID_ID);
		} catch (final UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Got a working service for an invalid token!");
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServicesImpl service implementation} if the token has been revoked by the user
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() {
		final SegmentEffortServices service = SegmentEffortServicesImpl.implementation(getRevokedToken());
		try {
			service.getSegmentEffort(TestUtils.SEGMENT_EFFORT_VALID_ID);
		} catch (final UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Revoked a token, but it's still useful");
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServicesImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the
	 * caching strategy is working)
	 * </p>
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() {
		final SegmentEffortServices service = SegmentEffortServicesImpl.implementation(TestUtils.getValidToken());
		final SegmentEffortServices service2 = SegmentEffortServicesImpl.implementation(TestUtils.getValidToken());
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServicesImpl service implementation} for a second, valid, different token, we get a DIFFERENT
	 * implementation
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		final SegmentEffortServices service = getService();
		final SegmentEffortServices service2 = getServiceWithoutWriteAccess();
		assertFalse(service == service2);
	}

	private SegmentEffortServices getService() {
		return SegmentEffortServicesImpl.implementation(TestUtils.getValidToken());
	}

	private String getRevokedToken() {
		return TestUtils.getRevokedToken();
	}

	private SegmentEffortServices getServiceWithoutWriteAccess() {
		return SegmentEffortServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
	}
}
