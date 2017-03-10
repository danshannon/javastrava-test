package test.service.impl.streamservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.service.Strava;
import test.api.model.StravaStreamTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#getEffortStreams(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetEffortStreamsTest extends ListMethodTest<StravaStream, Long> {
	@Override
	protected Long idInvalid() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_INVALID_ID;
	}

	@Override
	protected Long idPrivate() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ID;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID;
	}

	@Override
	protected Long idValidWithEntries() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaStream, Long> lister() {
		return ((strava, id) -> strava.getEffortStreams(id));
	}

	/**
	 * <p>
	 * Downsampled by distance
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetEffortStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final List<StravaStream> streams = TestUtils.strava().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, resolutionType, StravaStreamSeriesDownsamplingType.DISTANCE);
					validateList(streams);
				}
			}
		});
	}

	/**
	 * <p>
	 * Downsampled by time
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetEffortStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					final List<StravaStream> streams = TestUtils.strava().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, resolutionType, StravaStreamSeriesDownsamplingType.TIME);
					validateList(streams);
				}
			}
		});
	}

	/**
	 * <p>
	 * Invalid downsample resolution
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.strava().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamResolutionType.UNKNOWN, null);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * invalid downsample type
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.strava().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Invalid effort
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_invalidEffort() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaStream> streams = TestUtils.strava().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_INVALID_ID);
			assertNull(streams);
		});
	}

	/**
	 * <p>
	 * Invalid stream type
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.strava().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, null, null, StravaStreamType.UNKNOWN);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}
			fail("Should have got an IllegalArgumentException, but didn't"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Only one stream type
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetEffortStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaStream> streams = TestUtils.strava().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, null, null, StravaStreamType.DISTANCE);
			assertNotNull(streams);
			assertEquals(1, streams.size());
			assertEquals(StravaStreamType.DISTANCE, streams.get(0).getType());
			validateList(streams);
		});
	}

	/**
	 * <p>
	 * Effort for a private activity
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaStream> streams = TestUtils.stravaWithViewPrivate().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
			assertNotNull(streams);
			assertFalse(streams.isEmpty());
		});
	}

	/**
	 * <p>
	 * Effort for the authenticated user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	// 1. Valid effort for the authenticated user
	@Test
	public void testGetEffortStreams_validEffortAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaStream> streams = TestUtils.strava().getEffortStreams(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID);
			validateList(streams);
		});
	}

	@Override
	protected void validate(StravaStream object) {
		StravaStreamTest.validate(object);
	}

}
