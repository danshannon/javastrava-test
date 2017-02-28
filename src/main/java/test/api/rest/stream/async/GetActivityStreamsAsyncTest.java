/**
 *
 */
package test.api.rest.stream.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaStreamTest;
import test.api.rest.TestGetCallback;
import test.api.rest.stream.GetActivityStreamsTest;
import test.issues.strava.Issue21;
import test.issues.strava.Issue88;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author danshannon
 *
 */
public class GetActivityStreamsAsyncTest extends GetActivityStreamsTest {
	@Override
	protected TestGetCallback<StravaStream[], Long> getCallback() {
		return ((api, id) -> api.getActivityStreamsAsync(id, StravaStreamType.DISTANCE.toString(), null, null).get());
	}

	// 4. All stream types
	@Override
	public void testGetActivityStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api()
					.getActivityStreamsAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), null, null).get();
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
				// TODO This is a workaround for issue javastravav3api#21
				if (new Issue21().isIssue()) {
					return;
				}
				// End of workaround
				StravaStreamTest.validate(stream);
			}
		});
	}

	// 7. Downsampled by distance
	@Override
	public void testGetActivityStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api().getActivityStreamsAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER,
							getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.DISTANCE).get();
					assertNotNull(streams);
					for (final StravaStream stream : streams) {
						assertEquals(resolutionType, stream.getResolution());
					}
					validateArray(streams);
				}
			}
		});
	}

	// 6. Downsampled by time
	@Override
	public void testGetActivityStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					final StravaStream[] streams = api().getActivityStreamsAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER,
							getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.TIME).get();
					assertNotNull(streams);
					validateArray(streams);
				}
			}
		});
	}

	// 9. Invalid downsample resolution
	@Override
	public void testGetActivityStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreamsAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(),
						StravaStreamResolutionType.UNKNOWN, null).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution");
		});
	}

	// 10. Invalid downsample type (i.e. not distance or time)
	@Override
	public void testGetActivityStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreamsAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(),
						StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type");
		});
	}

	// 8. Invalid stream type
	@Override
	@Test
	public void testGetActivityStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue88().isIssue()) {
				return;
			}
			try {
				api().getActivityStreamsAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamType.UNKNOWN.getValue(), null,
						null).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have thrown a bad request exception");
		});
	}

	// 5. Only one stream type
	@Override
	public void testGetActivityStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getActivityStreamsAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER,
					StravaStreamType.DISTANCE.toString(), null, null).get();
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());
			// TODO This is a workaround for issue javastravav3api#21
			if (new Issue21().isIssue()) {
				return;
			}
			// End of workaround

			validateArray(streams);
		});
	}

	/**
	 * Test method for
	 * {@link javastrava.api.v3.service.impl.StreamServiceImpl#getActivityStreams(java.lang.String, javastrava.api.v3.model.reference.StravaStreamType[], javastrava.api.v3.model.reference.StravaStreamResolutionType, javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType)}
	 * .
	 *
	 * @throws Exception
	 */
	@Override
	// 1. Valid activity for the authenticated user
	public void get_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api()
					.getActivityStreamsAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), null, null).get();
			assertNotNull(streams);
			// TODO This is a workaround for issue javastravav3api#21
			if (new Issue21().isIssue()) {
				return;
			}
			// End of workaround

			validateArray(streams);
		});
	}

	// 3. Valid activity for other user
	@Override
	public void get_validBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreamsAsync(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, getAllStreamTypes(), null, null).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Shouldn't be able to return activity streams for activities that don't belong to the authenticated user");
		});
	}
}
