package test.api.rest.segment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetSegmentTest extends APITest {
	// 2. Invalid segment
	@Test
	public void testGetSegment_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegment(TestUtils.SEGMENT_INVALID_ID);
			} catch (NotFoundException e) {
				// expected
				return;
			}
			fail("Returned an invalid segment");
		});
	}

	// 3. Private segment belonging to another user
	@Test
	public void testGetSegment_otherUserPrivateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegment(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
			} catch (UnauthorizedException e) {
				// expected
				return;
			}
			fail("Got another user's private segment");
		});
	}

	// 4. Private segment belonging to the authenticated user
	@Test
	public void testGetSegment_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegment(TestUtils.SEGMENT_PRIVATE_ID);
			} catch (UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned a private segment without view_private access");
		});
	}

	@Test
	public void testGetSegment_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = apiWithViewPrivate().getSegment(TestUtils.SEGMENT_PRIVATE_ID);
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
			final StravaSegment segment = api().getSegment(TestUtils.SEGMENT_VALID_ID);
			assertNotNull(segment);
		});
	}

}
