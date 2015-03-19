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
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaStreamTest;
import test.api.service.StravaTest;
import test.utils.TestUtils;

public class GetActivityStreamsTest extends StravaTest {
	/**
	 * Test method for
	 * {@link javastrava.api.v3.service.impl.StreamServiceImpl#getActivityStreams(java.lang.String, javastrava.api.v3.model.reference.StravaStreamType[], javastrava.api.v3.model.reference.StravaStreamResolutionType, javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType)}
	 * .
	 *
	 * @throws UnauthorizedException
	 */
	// 1. Valid activity for the authenticated user
	@Test
	public void testGetActivityStreams_validActivityAuthenticatedUser() throws UnauthorizedException {
		final List<StravaStream> streams = service().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(streams);
		validateList(streams);
	}

	// 2. Invalid activity
	@Test
	public void testGetActivityStreams_invalidActivity() throws UnauthorizedException {
		final List<StravaStream> streams = service().getActivityStreams(TestUtils.ACTIVITY_INVALID);
		assertNull(streams);
	}

	// 3. Valid activity for other user
	@Test
	public void testGetActivityStreams_validActivityUnauthenticatedUser() {
		try {
			service().getActivityStreams(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
		} catch (final UnauthorizedException e) {
			// Expected
			return;
		}
		fail("Shouldn't be able to return activity streams for activities that don't belong to the authenticated user");
	}

	// 4. All stream types
	@Test
	public void testGetActivityStreams_allStreamTypes() throws UnauthorizedException {
		final List<StravaStream> streams = service().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(streams);
		int size = 0;
		for (final StravaStream stream : streams) {
			if (size == 0) {
				size = stream.getOriginalSize();
			}
			if (stream.getType() == StravaStreamType.MAPPOINT) {
				assertEquals(size, stream.getMapPoints().size());
			} else if (stream.getType() == StravaStreamType.MOVING) {
				assertEquals(size, stream.getMoving().size());
			} else {
				assertEquals(size, stream.getData().size());
			}
			assertNotNull(stream.getType());
			StravaStreamTest.validate(stream);
		}
	}

	// 5. Only one stream type
	@Test
	public void testGetActivityStreams_oneStreamType() throws UnauthorizedException {
		final List<StravaStream> streams = service().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null, StravaStreamType.DISTANCE);
		assertNotNull(streams);
		assertEquals(1, streams.size());
		assertEquals(StravaStreamType.DISTANCE, streams.get(0).getType());

		validateList(streams);
	}

	// 6. Downsampled by time
	@Test
	public void testGetActivityStreams_downsampledByTime() throws UnauthorizedException {
		for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
			if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
				final List<StravaStream> streams = service().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, resolutionType,
						StravaStreamSeriesDownsamplingType.TIME);
				assertNotNull(streams);
				validateList(streams);
			}
		}
	}

	// 7. Downsampled by distance
	@Test
	public void testGetActivityStreams_downsampledByDistance() throws UnauthorizedException {
		for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
			if (resolutionType != StravaStreamResolutionType.UNKNOWN && resolutionType != null) {
				final List<StravaStream> streams = service().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, resolutionType,
						StravaStreamSeriesDownsamplingType.DISTANCE);
				assertNotNull(streams);
				for (final StravaStream stream : streams) {
					assertEquals(resolutionType, stream.getResolution());
				}
				validateList(streams);
			}
		}
	}

	// 8. Invalid stream type
	@Test
	public void testGetActivityStreams_invalidStreamType() throws UnauthorizedException {
		try {
			service().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null, StravaStreamType.UNKNOWN);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Should have thrown an illegal argument exception");
	}

	// 9. Invalid downsample resolution
	@Test
	public void testGetActivityStreams_invalidDownsampleResolution() throws UnauthorizedException {
		try {
			service().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamResolutionType.UNKNOWN, null);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Didn't throw an exception when asking for an invalid downsample resolution");
	}

	// 10. Invalid downsample type (i.e. not distance or time)
	@Test
	public void testGetActivityStreams_invalidDownsampleType() throws UnauthorizedException {
		try {
			service().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Didn't throw an exception when asking for an invalid downsample type");
	}

	private void validateList(final List<StravaStream> streams) {
		assertNotNull(streams);
		for (final StravaStream stream : streams) {
			StravaStreamTest.validate(stream);
		}
	}

}
