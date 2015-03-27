package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetSegmentTest extends StravaTest {
	// 2. Invalid segment
	@Test
	public void testGetSegment_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = strava().getSegment(TestUtils.SEGMENT_INVALID_ID);
			assertNull(segment);
		});
	}

	// 3. Private segment belonging to another user
	@Test
	public void testGetSegment_otherUserPrivateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = strava().getSegment(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
			assertEquals(StravaResourceState.META, segment.getResourceState());
		});
	}

	// 4. Private segment belonging to the authenticated user
	@Test
	public void testGetSegment_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = strava().getSegment(TestUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(segment);
			StravaSegmentTest.validateSegment(segment, TestUtils.SEGMENT_PRIVATE_ID, StravaResourceState.META);
		});
	}
	
	@Test
	public void testGetSegment_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = stravaWithViewPrivate().getSegment(TestUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(segment);
			assertEquals(Boolean.TRUE, segment.getPrivateSegment());
			StravaSegmentTest.validateSegment(segment, TestUtils.SEGMENT_PRIVATE_ID, StravaResourceState.DETAILED);
		});
	}

	// Test cases:
	// 1. Valid segment
	@Test
	public void testGetSegment_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = strava().getSegment(TestUtils.SEGMENT_VALID_ID);
			assertNotNull(segment);
		});
	}

}
