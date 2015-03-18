package test.api.service.impl.streamservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.service.StreamService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.StreamServiceImpl;

import org.junit.Test;

import test.api.model.StravaStreamTest;
import test.utils.TestUtils;

public class GetSegmentStreamsTest {
	/**
	 * Test method for
	 * {@link javastrava.api.v3.service.impl.StreamServiceImpl#getSegmentStreams(java.lang.String, javastrava.api.v3.model.reference.StravaStreamType[], javastrava.api.v3.model.reference.StravaStreamResolutionType, javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType)}
	 * .
	 */
	@Test
	// 1. Valid segment for the authenticated user
	public void testGetSegmentStreams_validSegment() throws UnauthorizedException {
		final StreamService service = getService();
		final List<StravaStream> streams = service.getSegmentStreams(TestUtils.SEGMENT_VALID_ID);
		validateList(streams);
	}

	// 2. Invalid segment
	@Test
	public void testGetSegmentStreams_invalidSegment() throws UnauthorizedException {
		final StreamService service = getService();
		final List<StravaStream> streams = service.getSegmentStreams(TestUtils.SEGMENT_INVALID_ID);
		assertNull(streams);
	}

	// 3. Valid segment which is private and belongs to another user
	@Test
	public void testGetSegmentStreams_validSegmentUnauthenticatedUser() {
		final StreamService service = getService();
		try {
			service.getSegmentStreams(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
		} catch (final UnauthorizedException e) {
			// Expected
			return;
		}
		fail("Shouldn't be able to return activity streams for private segments that don't belong to the authenticated user");
	}

	// 4. All stream types
	@Test
	public void testGetSegmentStreams_allStreamTypes() throws UnauthorizedException {
		final StreamService service = getService();
		final List<StravaStream> streams = service.getSegmentStreams(TestUtils.SEGMENT_VALID_ID);
		validateList(streams);
	}

	// 5. Only one stream type
	@Test
	public void testGetSegmentStreams_oneStreamType() throws UnauthorizedException {
		final StreamService service = getService();
		final List<StravaStream> streams = service.getSegmentStreams(TestUtils.SEGMENT_VALID_ID, null, null, StravaStreamType.DISTANCE);
		assertNotNull(streams);
		assertEquals(1, streams.size());
		assertEquals(StravaStreamType.DISTANCE, streams.get(0).getType());
		validateList(streams);
	}

	// 6. Downsampled by time - can't be done for segment streams as there's no time element
	@Test
	public void testGetSegmentStreams_downsampledByTime() throws UnauthorizedException {
		final StreamService service = getService();
		for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
			if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
				try {
					service.getSegmentStreams(TestUtils.SEGMENT_VALID_ID, resolutionType, StravaStreamSeriesDownsamplingType.TIME);
				} catch (final IllegalArgumentException e) {
					// expected
					return;
				}
				fail("Can't return a segment stream which is downsampled by TIME!");
			}
		}
	}

	// 7. Downsampled by distance
	@Test
	public void testGetSegmentStreams_downsampledByDistance() throws UnauthorizedException {
		final StreamService service = getService();
		for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
			if (resolutionType != StravaStreamResolutionType.UNKNOWN && resolutionType != null) {
				final List<StravaStream> streams = service.getSegmentStreams(TestUtils.SEGMENT_VALID_ID, resolutionType, StravaStreamSeriesDownsamplingType.DISTANCE);
				validateList(streams);
			}
		}
	}

	// 8. Invalid stream type
	@Test
	public void testGetSegmentStreams_invalidStreamType() throws UnauthorizedException {
		final StreamService service = getService();
		try {
			service.getSegmentStreams(TestUtils.SEGMENT_VALID_ID, null, null, StravaStreamType.UNKNOWN);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Should have got an IllegalArgumentException, but didn't");
	}

	// 9. Invalid downsample resolution
	@Test
	public void testGetSegmentStreams_invalidDownsampleResolution() throws UnauthorizedException {
		final StreamService service = getService();
		try {
			service.getSegmentStreams(TestUtils.SEGMENT_VALID_ID, StravaStreamResolutionType.UNKNOWN, null);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Didn't throw an exception when asking for an invalid downsample resolution");
	}

	// 10. Invalid downsample type (i.e. not distance or time)
	@Test
	public void testGetSegmentStreams_invalidDownsampleType() throws UnauthorizedException {
		final StreamService service = getService();
		try {
			service.getSegmentStreams(TestUtils.SEGMENT_VALID_ID, StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Didn't throw an exception when asking for an invalid downsample type");
	}

	private StreamService getService() {
		return StreamServiceImpl.instance(TestUtils.getValidToken());
	}

	private void validateList(final List<StravaStream> streams) {
		for (final StravaStream stream : streams) {
			StravaStreamTest.validate(stream);
		}
	}
}
