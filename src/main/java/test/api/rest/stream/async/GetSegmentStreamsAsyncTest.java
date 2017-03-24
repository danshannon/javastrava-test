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
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.BadRequestException;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.stream.GetSegmentStreamsTest;
import test.issues.strava.Issue89;
import test.issues.strava.Issue90;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#getSegmentStreamsAsync(Integer, String, StravaStreamResolutionType, StravaStreamSeriesDownsamplingType)}
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class GetSegmentStreamsAsyncTest extends GetSegmentStreamsTest {

	@Override
	protected APIGetCallback<StravaStream[], Integer> getter() {
		return ((api, id) -> api.getSegmentStreamsAsync(id, StravaStreamType.DISTANCE.toString(), null, null).get());
	}

	@Override
	@Test
	public void testGetSegmentStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getSegmentStreamsAsync(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), null, null).get();
			validateArray(streams);
		});
	}

	@Override
	@Test
	public void testGetSegmentStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api().getSegmentStreamsAsync(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.DISTANCE)
							.get();
					validateArray(streams);
				}
			}
		});
	}

	@Override
	@Test
	public void testGetSegmentStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue89().isIssue()) {
				return;
			}
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					try {
						api().getSegmentStreamsAsync(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.TIME).get();
					} catch (final BadRequestException e) {
						// expected
						return;
					}
					fail("Can't return a segment stream which is downsampled by TIME!"); //$NON-NLS-1$
				}
			}
		});
	}

	@Override
	@Test
	public void testGetSegmentStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreamsAsync(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN, null).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testGetSegmentStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreamsAsync(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testGetSegmentStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue90().isIssue()) {
				return;
			}
			try {
				api().getSegmentStreamsAsync(SegmentDataUtils.SEGMENT_VALID_ID, StravaStreamType.UNKNOWN.toString(), null, null).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have got a BadRequestException, but didn't"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testGetSegmentStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getSegmentStreamsAsync(SegmentDataUtils.SEGMENT_VALID_ID, StravaStreamType.DISTANCE.toString(), null, null).get();
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());
			validateArray(streams);
		});
	}
}
