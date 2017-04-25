package test.api.stream;

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
import test.api.APIGetTest;
import test.api.callback.APIGetCallback;
import test.issues.strava.Issue21;
import test.issues.strava.Issue88;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.StreamDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#getActivityStreams(Long, String, StravaStreamResolutionType, StravaStreamSeriesDownsamplingType)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetActivityStreamsTest extends APIGetTest<StravaStream[], Long> {
	/**
	 * @return List of all valid stream types that can be requested
	 */
	protected static String getAllStreamTypes() {
		final StravaStreamType[] types = StravaStreamType.values();
		String list = ""; //$NON-NLS-1$

		for (final StravaStreamType type : types) {
			if (type != StravaStreamType.UNKNOWN) {
				list = list + type.getValue() + ","; //$NON-NLS-1$
			}
		}
		return list;
	}

	@Override
	public void get_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), null, null);
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
				api().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, getAllStreamTypes(), null, null);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Shouldn't be able to return activity streams for activities that don't belong to the authenticated user"); //$NON-NLS-1$
		});
	}

	@Override
	protected APIGetCallback<StravaStream[], Long> getter() {
		return ((api, id) -> api.getActivityStreams(id, StravaStreamType.DISTANCE.toString(), null, null));
	}

	@Override
	protected Long invalidId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long privateId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long privateIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * All stream types
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testGetActivityStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), null, null);
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

	/**
	 * Downsampled by distance
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetActivityStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), resolutionType,
							StravaStreamSeriesDownsamplingType.DISTANCE);
					assertNotNull(streams);
					for (final StravaStream stream : streams) {
						assertEquals(resolutionType, stream.getResolution());
					}
					validateArray(streams);
				}
			}
		});
	}

	/**
	 * Downsampled by time
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetActivityStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					final StravaStream[] streams = api().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), resolutionType,
							StravaStreamSeriesDownsamplingType.TIME);
					assertNotNull(streams);
					validateArray(streams);
				}
			}
		});
	}

	/**
	 * Invalid downsample resolution
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivityStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution"); //$NON-NLS-1$
		});
	}

	/**
	 * Invalid downsample type (i.e. not distance or time)
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivityStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type"); //$NON-NLS-1$
		});
	}

	/**
	 * Invalid stream type
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivityStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue88().isIssue()) {
				return;
			}
			try {
				api().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamType.UNKNOWN.getValue(), null, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have thrown a bad request exception"); //$NON-NLS-1$
		});
	}

	/**
	 * Only one stream type
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetActivityStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamType.DISTANCE.toString(), null, null);
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());

			if (new Issue21().isIssue()) {
				return;
			}

			validateArray(streams);
		});
	}

	@Override
	protected void validate(final StravaStream[] results) throws Exception {
		for (final StravaStream stream : results) {
			StreamDataUtils.validateStream(stream);
		}

	}

	/**
	 * Validate an array of streams
	 *
	 * @param streams
	 *            The streams to validate
	 */
	@SuppressWarnings("static-method")
	protected void validateArray(final StravaStream[] streams) {
		assertNotNull(streams);
		for (final StravaStream stream : streams) {
			StreamDataUtils.validateStream(stream);
		}
	}

	@Override
	protected Long validId() {
		return ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	@Override
	protected Long validIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}
}
