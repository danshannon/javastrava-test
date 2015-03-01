package test.api.service.impl.retrofit.segmentservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.service.SegmentServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;

import org.junit.Test;

import test.utils.TestUtils;

public class GetSegmentTest {
	// Test cases:
	// 1. Valid segment
	@Test
	public void testGetSegment_validSegment() throws UnauthorizedException {
		final SegmentServices service = service();
		final StravaSegment segment = service.getSegment(TestUtils.SEGMENT_VALID_ID);
		assertNotNull(segment);
	}

	// 2. Invalid segment
	@Test
	public void testGetSegment_invalidSegment() throws UnauthorizedException {
		final SegmentServices service = service();
		final StravaSegment segment = service.getSegment(TestUtils.SEGMENT_INVALID_ID);
		assertNull(segment);
	}

	// 3. Private segment belonging to another user
	@Test
	public void testGetSegment_otherUserPrivateSegment() throws UnauthorizedException {
		final SegmentServices service = service();
		try {
			service.getSegment(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
		} catch (final UnauthorizedException e) {
			// Expected
			return;
		}
		fail("Returned segment details for a private segment that belongs to another user");
	}

	// 4. Private segment belonging to the authenticated user
	@Test
	public void testGetSegment_private() throws UnauthorizedException {
		final SegmentServices service = service();
		final StravaSegment segment = service.getSegment(TestUtils.SEGMENT_PRIVATE_ID);
		assertNotNull(segment);
		assertEquals(TestUtils.SEGMENT_PRIVATE_ID, segment.getId());
	}

	private SegmentServices service() {
		return SegmentServicesImpl.implementation(TestUtils.getValidToken());
	}


}
