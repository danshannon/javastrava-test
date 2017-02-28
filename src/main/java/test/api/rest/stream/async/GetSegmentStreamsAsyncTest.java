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
import test.api.rest.TestGetCallback;
import test.api.rest.stream.GetSegmentStreamsTest;
import test.issues.strava.Issue89;
import test.issues.strava.Issue90;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author danshannon
 *
 */
public class GetSegmentStreamsAsyncTest extends GetSegmentStreamsTest {

	@Override
	protected TestGetCallback<StravaStream[], Integer> getCallback() {
		return ((api, id) -> api.getSegmentStreamsAsync(id, StravaStreamType.DISTANCE.toString(), null, null).get());
	}

	// 4. All stream types
	@Override
	@Test
	public void testGetSegmentStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getSegmentStreamsAsync(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(), null, null)
					.get();
			validateArray(streams);
		});
	}

	// 7. Downsampled by distance
	@Override
	@Test
	public void testGetSegmentStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api().getSegmentStreamsAsync(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(),
							resolutionType, StravaStreamSeriesDownsamplingType.DISTANCE).get();
					validateArray(streams);
				}
			}
		});
	}

	// 6. Downsampled by time - can't be done for segment streams as there's no
	// time element
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
						api().getSegmentStreamsAsync(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(), resolutionType,
								StravaStreamSeriesDownsamplingType.TIME).get();
					} catch (final BadRequestException e) {
						// expected
						return;
					}
					fail("Can't return a segment stream which is downsampled by TIME!");
				}
			}
		});
	}

	// 9. Invalid downsample resolution
	@Override
	@Test
	public void testGetSegmentStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreamsAsync(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN,
						null).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution");
		});
	}

	// 10. Invalid downsample type (i.e. not distance or time)
	@Override
	@Test
	public void testGetSegmentStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreamsAsync(TestUtils.SEGMENT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.LOW,
						StravaStreamSeriesDownsamplingType.UNKNOWN).get();
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
	public void testGetSegmentStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue90().isIssue()) {
				return;
			}
			try {
				api().getSegmentStreamsAsync(TestUtils.SEGMENT_VALID_ID, StravaStreamType.UNKNOWN.toString(), null, null).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have got a BadRequestException, but didn't");
		});
	}

	// 5. Only one stream type
	@Override
	@Test
	public void testGetSegmentStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api()
					.getSegmentStreamsAsync(TestUtils.SEGMENT_VALID_ID, StravaStreamType.DISTANCE.toString(), null, null).get();
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());
			validateArray(streams);
		});
	}
}
