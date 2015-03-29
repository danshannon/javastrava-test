package test.api.service.impl.streamservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaStreamTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetSegmentStreamsTest extends StravaTest {
	// 4. All stream types
	@Test
	public void testGetSegmentStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaStream> streams = strava().getSegmentStreams(TestUtils.SEGMENT_VALID_ID);
			validateList(streams);
		});
	}

	// 7. Downsampled by distance
	@Test
	public void testGetSegmentStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final List<StravaStream> streams = strava().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, resolutionType,
							StravaStreamSeriesDownsamplingType.DISTANCE);
					validateList(streams);
				}
			}
		});
	}

	// 6. Downsampled by time - can't be done for segment streams as there's no time element
	@Test
	public void testGetSegmentStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					try {
						strava().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, resolutionType, StravaStreamSeriesDownsamplingType.TIME);
					} catch (final IllegalArgumentException e) {
						// expected
						return;
					}
					fail("Can't return a segment stream which is downsampled by TIME!");
				}
			}
		})	  ;
	}

	// 9. Invalid downsample resolution
	@Test
	public void testGetSegmentStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				strava().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, StravaStreamResolutionType.UNKNOWN, null);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution");
		});
	}

	// 10. Invalid downsample type (i.e. not distance or time)
	@Test
	public void testGetSegmentStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				strava().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type");
		});
	}

	// 2. Invalid segment
	@Test
	public void testGetSegmentStreams_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaStream> streams = strava().getSegmentStreams(TestUtils.SEGMENT_INVALID_ID);
			assertNull(streams);
		});
	}

	// 8. Invalid stream type
	@Test
	public void testGetSegmentStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				strava().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, null, null, StravaStreamType.UNKNOWN);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}
			fail("Should have got an IllegalArgumentException, but didn't");
		});
	}

	// 5. Only one stream type
	@Test
	public void testGetSegmentStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaStream> streams = strava().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, null, null, StravaStreamType.DISTANCE);
			assertNotNull(streams);
			assertEquals(1, streams.size());
			assertEquals(StravaStreamType.DISTANCE, streams.get(0).getType());
			validateList(streams);
		});
	}

	/**
	 * Test method for
	 * {@link javastrava.api.v3.service.impl.StreamServiceImpl#getSegmentStreams(java.lang.String, javastrava.api.v3.model.reference.StravaStreamType[], javastrava.api.v3.model.reference.StravaStreamResolutionType, javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType)}
	 * .
	 *
	 * @throws Exception
	 */
	@Test
	// 1. Valid segment for the authenticated user
	public void testGetSegmentStreams_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaStream> streams = strava().getSegmentStreams(TestUtils.SEGMENT_VALID_ID);
			validateList(streams);
		});
	}

	// 3. Valid segment which is private and belongs to another user
	@Test
	public void testGetSegmentStreams_validSegmentUnauthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				strava().getSegmentStreams(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Shouldn't be able to return activity streams for private segments that don't belong to the authenticated user");
		});
	}

	@Test
	public void testGetSegmentStreams_privateSegmentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaStream> streams = stravaWithViewPrivate().getSegmentStreams(TestUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(streams);
			assertFalse(streams.isEmpty());
		});
	}

	@Test
	public void testGetSegmentStreams_privateSegmentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaStream> streams = strava().getSegmentStreams(TestUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(streams);
			assertTrue(streams.isEmpty());
		});
	}

	private void validateList(final List<StravaStream> streams) {
		for (final StravaStream stream : streams) {
			StravaStreamTest.validate(stream);
		}
	}
}
