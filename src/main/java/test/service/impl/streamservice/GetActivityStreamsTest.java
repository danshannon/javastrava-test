package test.service.impl.streamservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.service.Strava;
import test.issues.strava.Issue88;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.StreamDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#getActivityStreams(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetActivityStreamsTest extends ListMethodTest<StravaStream, Long> {

	@Override
	protected Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaStream, Long> lister() {
		return ((strava, id) -> strava.getActivityStreams(id));
	}

	/**
	 * <p>
	 * All stream types
	 * </p>
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivityStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaStream> streams = TestUtils.strava().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertNotNull(streams);
			int size = 0;
			for (final StravaStream stream : streams) {
				if (size == 0) {
					size = stream.getOriginalSize().intValue();
				}
				if (stream.getType() == StravaStreamType.MAPPOINT) {
					assertEquals(size, stream.getMapPoints().size());
				} else if (stream.getType() == StravaStreamType.MOVING) {
					assertEquals(size, stream.getMoving().size());
				} else {
					assertEquals(size, stream.getData().size());
				}
				assertNotNull(stream.getType());
				StreamDataUtils.validateStream(stream);
			}
		});
	}

	/**
	 * <p>
	 * Downsampled by distance
	 * </p>
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@Test
	public void testGetActivityStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final List<StravaStream> streams = TestUtils.strava().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, resolutionType,
							StravaStreamSeriesDownsamplingType.DISTANCE);
					assertNotNull(streams);
					for (final StravaStream stream : streams) {
						assertEquals(resolutionType, stream.getResolution());
					}
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
	 *             if an unexpected error occurs
	 */
	@Test
	public void testGetActivityStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					final List<StravaStream> streams = TestUtils.strava().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, resolutionType,
							StravaStreamSeriesDownsamplingType.TIME);
					assertNotNull(streams);
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
	 *             if an unexpected error occurs
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivityStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.strava().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamResolutionType.UNKNOWN, null);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Invalid downsample type
	 * </p>
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivityStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.strava().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Invalid sample type
	 * </p>
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivityStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue88().isIssue()) {
				return;
			}
			// End of workaround

			try {
				TestUtils.strava().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null, StravaStreamType.UNKNOWN);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}
			fail("Should have thrown an illegal argument exception"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Only one stream type
	 * </p>
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@Test
	public void testGetActivityStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaStream> streams = TestUtils.strava().getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null, StravaStreamType.DISTANCE);
			assertNotNull(streams);
			assertEquals(1, streams.size());
			assertEquals(StravaStreamType.DISTANCE, streams.get(0).getType());

			validateList(streams);
		});
	}

	@Override
	protected void validate(StravaStream object) {
		StreamDataUtils.validateStream(object);
	}

	@Override
	protected Class<StravaStream> classUnderTest() {
		return StravaStream.class;
	}

}
