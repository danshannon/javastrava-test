package test.api.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
import test.issues.strava.Issue78;
import test.issues.strava.Issue87;
import test.issues.strava.Issue91;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.service.standardtests.data.StreamDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#getEffortStreams(Long, String, StravaStreamResolutionType, StravaStreamSeriesDownsamplingType)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetEffortStreamsTest extends APIGetTest<StravaStream[], Long> {
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

	/**
	 * Validate an array of streams
	 *
	 * @param streams
	 *            The streams to be validated
	 */
	protected static void validateArray(final StravaStream[] streams) {
		for (final StravaStream stream : streams) {
			StreamDataUtils.validateStream(stream);
		}
	}

	@Override
	public void get_privateWithoutViewPrivate() throws Exception {
		if (new Issue78().isIssue()) {
			return;
		}

		super.get_privateWithoutViewPrivate();
	}

	@Override
	protected APIGetCallback<StravaStream[], Long> getter() {
		return ((api, id) -> api.getEffortStreams(id, StravaStreamType.DISTANCE.toString(), null, null));
	}

	@Override
	protected Long invalidId() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_INVALID_ID;
	}

	@Override
	protected Long privateId() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ID;
	}

	@Override
	protected Long privateIdBelongsToOtherUser() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID;
	}

	/**
	 * All stream types
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), null, null);
			validateArray(streams);
		});
	}

	/**
	 * Downsampled by distance
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), resolutionType,
							StravaStreamSeriesDownsamplingType.DISTANCE);
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
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					final StravaStream[] streams = api().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.TIME);
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
	public void testGetEffortStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN, null);
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
	public void testGetEffortStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN);
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
	public void testGetEffortStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue91().isIssue()) {
				return;
			}

			try {
				api().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamType.UNKNOWN.toString(), null, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have got an BadRequestException, but didn't"); //$NON-NLS-1$
		});
	}

	/**
	 * Only one stream type
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamType.DISTANCE.toString(), null, null);
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());
			validateArray(streams);
		});
	}

	/**
	 * Attempt to get effort streams for a segment effort belonging to an activity flagged as private, using a token WITHOUT view_private scope
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID, getAllStreamTypes(), null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned effort streams for an effort on a private activity, but without view_private"); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to get effort streams for a segment effort belonging to an activity flagged as private, using a token WITH view_private scope
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = apiWithViewPrivate().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID, getAllStreamTypes(), null, null);
			assertNotNull(streams);
			assertFalse(streams.length == 0);
		});
	}

	/**
	 * Valid effort for other user
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_privateEffortUnauthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID, getAllStreamTypes(), null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned effort streams for a private effort belonging to another user"); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to get effort streams for a segment effort belonging to an segment flagged as private, using a token WITHOUT view_private scope
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_privateSegmentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue87().isIssue()) {
				return;
			}

			final StravaStream[] streams = api().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ID, getAllStreamTypes(), null, null);
			assertNotNull(streams);
			assertTrue(streams.length == 0);
		});
	}

	/**
	 * Attempt to get effort streams for a segment effort belonging to an segment flagged as private, using a token WITH view_private scope
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_privateSegmentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = apiWithViewPrivate().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ID, getAllStreamTypes(), null, null);
			assertNotNull(streams);
			assertFalse(streams.length == 0);
		});
	}

	@Override
	protected void validate(final StravaStream[] result) throws Exception {
		for (final StravaStream stream : result) {
			StreamDataUtils.validateStream(stream);
		}

	}

	@Override
	protected Long validId() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID;
	}

	@Override
	protected Long validIdBelongsToOtherUser() {
		return null;
	}

}
