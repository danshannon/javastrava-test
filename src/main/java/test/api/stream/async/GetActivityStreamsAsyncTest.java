/**
 *
 */
package test.api.stream.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaStream;
import javastrava.model.reference.StravaStreamResolutionType;
import javastrava.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.model.reference.StravaStreamType;
import javastrava.service.exception.BadRequestException;
import javastrava.service.exception.UnauthorizedException;
import test.api.callback.APIGetCallback;
import test.api.stream.GetActivityStreamsTest;
import test.issues.strava.Issue21;
import test.issues.strava.Issue88;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.StreamDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#getActivityStreamsAsync(Long, String, StravaStreamResolutionType, StravaStreamSeriesDownsamplingType)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetActivityStreamsAsyncTest extends GetActivityStreamsTest {
	@Override
	public void get_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getActivityStreamsAsync(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), null, null).get();
			assertNotNull(streams);
			if (new Issue21().isIssue()) {
				return;
			}

			validateArray(streams);
		});
	}

	@Override
	public void get_validBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreamsAsync(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, getAllStreamTypes(), null, null).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Shouldn't be able to return activity streams for activities that don't belong to the authenticated user"); //$NON-NLS-1$
		});
	}

	@Override
	protected APIGetCallback<StravaStream[], Long> getter() {
		return ((api, id) -> api.getActivityStreamsAsync(id, StravaStreamType.DISTANCE.toString(), null, null).get());
	}

	@SuppressWarnings("boxing")
	@Override
	public void testGetActivityStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getActivityStreamsAsync(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), null, null).get();
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

				if (new Issue21().isIssue()) {
					return;
				}

				StreamDataUtils.validateStream(stream);
			}
		});
	}

	@Override
	public void testGetActivityStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api()
							.getActivityStreamsAsync(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.DISTANCE).get();
					assertNotNull(streams);
					for (final StravaStream stream : streams) {
						assertEquals(resolutionType, stream.getResolution());
					}
					validateArray(streams);
				}
			}
		});
	}

	@Override
	public void testGetActivityStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					final StravaStream[] streams = api()
							.getActivityStreamsAsync(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.TIME).get();
					assertNotNull(streams);
					validateArray(streams);
				}
			}
		});
	}

	@Override
	public void testGetActivityStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreamsAsync(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN, null).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution"); //$NON-NLS-1$
		});
	}

	@Override
	public void testGetActivityStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreamsAsync(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testGetActivityStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue88().isIssue()) {
				return;
			}
			try {
				api().getActivityStreamsAsync(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamType.UNKNOWN.getValue(), null, null).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have thrown a bad request exception"); //$NON-NLS-1$
		});
	}

	@Override
	public void testGetActivityStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getActivityStreamsAsync(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamType.DISTANCE.toString(), null, null).get();
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());

			if (new Issue21().isIssue()) {
				return;
			}

			validateArray(streams);
		});
	}
}
