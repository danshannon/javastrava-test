package test.api.rest.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaStreamTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetSegmentStreamsTest extends APITest {
	// 4. All stream types
	@Test
	public void testGetSegmentStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(), null, null);
			validateArray(streams);
		});
	}

	// 7. Downsampled by distance
	@Test
	public void testGetSegmentStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(), resolutionType,
							StravaStreamSeriesDownsamplingType.DISTANCE);
					validateArray(streams);
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
						api().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.TIME);
					} catch (final BadRequestException e) {
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
				api().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN, null);
			} catch (final BadRequestException e) {
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
				api().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.LOW,
						StravaStreamSeriesDownsamplingType.UNKNOWN);
			} catch (final BadRequestException e) {
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
			try {
				api().getSegmentStreams(TestUtils.SEGMENT_INVALID_ID, getAllStreamTypes(), null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned segment streams for an invalid segment");
		});
	}

	// 8. Invalid stream type
	@Test
	public void testGetSegmentStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, StravaStreamType.UNKNOWN.toString(), null, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have got a BadRequestException, but didn't");
		});
	}

	// 5. Only one stream type
	@Test
	public void testGetSegmentStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, StravaStreamType.DISTANCE.toString(), null, null);
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());
			validateArray(streams);
		});
	}

	/**
	 * Test method for
	 * {@link javaapi.api.v3.service.impl.StreamServiceImpl#getSegmentStreams(java.lang.String, javaapi.api.v3.model.reference.StravaStreamType[], javaapi.api.v3.model.reference.StravaStreamResolutionType, javaapi.api.v3.model.reference.StravaStreamSeriesDownsamplingType)}
	 * .
	 *
	 * @throws Exception
	 */
	@Test
	// 1. Valid segment for the authenticated user
	public void testGetSegmentStreams_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getSegmentStreams(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(), null, null);
			validateArray(streams);
		});
	}

	// 3. Valid segment which is private and belongs to another user
	@Test
	public void testGetSegmentStreams_validSegmentUnauthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreams(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID, getAllStreamTypes(), null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned segment streams for a private segment belonging to another user");
		});
	}

	@Test
	public void testGetSegmentStreams_privateSegmentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = apiWithViewPrivate().getSegmentStreams(TestUtils.SEGMENT_PRIVATE_ID, getAllStreamTypes(), null, null);
			assertNotNull(streams);
			assertFalse(streams.length == 0);
		});
	}

	@Test
	public void testGetSegmentStreams_privateSegmentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreams(TestUtils.SEGMENT_PRIVATE_ID, getAllStreamTypes(), null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned segment streams for a private segment, without view_private access");
		});
	}

	private void validateArray(final StravaStream[] streams) {
		for (final StravaStream stream : streams) {
			StravaStreamTest.validate(stream);
		}
	}

	/**
	 * @return List of all valid stream types that can be requested
	 */
	private static String getAllStreamTypes() {
		final StravaStreamType[] types = StravaStreamType.values();
		String list = "";

		for (final StravaStreamType type : types) {
			if (type != StravaStreamType.UNKNOWN) {
				list = list + type.getValue() + ",";
			}
		}
		return list;
	}
}
