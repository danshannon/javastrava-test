package test.api.rest.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.service.exception.BadRequestException;
import test.api.model.StravaStreamTest;
import test.api.rest.APIGetTest;
import test.api.rest.TestGetCallback;
import test.issues.strava.Issue87;
import test.issues.strava.Issue89;
import test.issues.strava.Issue90;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;

public class GetSegmentStreamsTest extends APIGetTest<StravaStream[], Integer> {
	/**
	 * @return List of all valid stream types that can be requested
	 */
	protected static String getAllStreamTypes() {
		final StravaStreamType[] types = StravaStreamType.values();
		String list = "";

		for (final StravaStreamType type : types) {
			if (type != StravaStreamType.UNKNOWN) {
				list = list + type.getValue() + ",";
			}
		}
		return list;
	}

	// 4. All stream types
	@Test
	public void testGetSegmentStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), null, null);
			validateArray(streams);
		});
	}

	// 7. Downsampled by distance
	@Test
	public void testGetSegmentStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(),
							resolutionType, StravaStreamSeriesDownsamplingType.DISTANCE);
					validateArray(streams);
				}
			}
		});
	}

	// 6. Downsampled by time - can't be done for segment streams as there's no
	// time element
	@Test
	public void testGetSegmentStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue89().isIssue()) {
				return;
			}
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					try {
						api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), resolutionType,
								StravaStreamSeriesDownsamplingType.TIME);
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
	@Test
	public void testGetSegmentStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution");
		});
	}

	// 10. Invalid downsample type (i.e. not distance or time)
	@Test
	public void testGetSegmentStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.LOW,
						StravaStreamSeriesDownsamplingType.UNKNOWN);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type");
		});
	}

	// 8. Invalid stream type
	@Test
	public void testGetSegmentStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue90().isIssue()) {
				return;
			}
			try {
				api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, StravaStreamType.UNKNOWN.toString(), null, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have got a BadRequestException, but didn't");
		});
	}

	// 5. Only one stream type
	@Test
	public void testGetSegmentStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, StravaStreamType.DISTANCE.toString(),
					null, null);
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());
			validateArray(streams);
		});
	}

	protected void validateArray(final StravaStream[] streams) {
		for (final StravaStream stream : streams) {
			StravaStreamTest.validate(stream);
		}
	}

	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return SegmentDataUtils.SEGMENT_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return SegmentDataUtils.SEGMENT_PRIVATE_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return SegmentDataUtils.SEGMENT_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaStream[] result) throws Exception {
		for (final StravaStream stream : result) {
			StravaStreamTest.validate(stream);
		}

	}

	/**
	 * @see test.api.rest.APIGetTest#get_privateWithoutViewPrivate()
	 */
	@Override
	public void get_privateWithoutViewPrivate() throws Exception {
		// TODO Workaround for issue #87
		if (new Issue87().isIssue()) {
			return;
		}
		super.get_privateWithoutViewPrivate();
	}

	@Override
	protected TestGetCallback<StravaStream[], Integer> getCallback() {
		return ((api, id) -> api.getSegmentStreams(id, StravaStreamType.DISTANCE.toString(), null, null));
	}
}
