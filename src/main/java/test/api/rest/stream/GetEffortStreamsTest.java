package test.api.rest.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaStreamTest;
import test.api.rest.APITest;
import test.issues.strava.Issue87;
import test.issues.strava.Issue91;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetEffortStreamsTest extends APITest {
	// 4. All stream types
	@Test
	public void testGetEffortStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), null, null);
			validateArray(streams);
		});
	}

	// 7. Downsampled by distance
	@Test
	public void testGetEffortStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), resolutionType,
							StravaStreamSeriesDownsamplingType.DISTANCE);
					validateArray(streams);
				}
			}
		});
	}

	// 6. Downsampled by time
	@Test
	public void testGetEffortStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					final StravaStream[] streams = api().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), resolutionType,
							StravaStreamSeriesDownsamplingType.TIME);
					validateArray(streams);
				}
			}
		});
	}

	// 9. Invalid downsample resolution
	@Test
	public void testGetEffortStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution");
		});
	}

	// 10. Invalid downsample type (i.e. not distance or time)
	@Test
	public void testGetEffortStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.LOW,
						StravaStreamSeriesDownsamplingType.UNKNOWN);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type");
		});
	}

	// 2. Invalid effort
	@Test
	public void testGetEffortStreams_invalidEffort() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreams(TestUtils.SEGMENT_EFFORT_INVALID_ID, getAllStreamTypes(), null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned effort streams for a non-existent segment effort");
		});
	}

	// 8. Invalid stream type
	@Test
	public void testGetEffortStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO Workaround for issue javastravav3api#91
				if (new Issue91().isIssue()) {
					return;
				}
				// End of workaround

				try {
					api().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamType.UNKNOWN.toString(), null, null);
				} catch (final BadRequestException e) {
					// Expected
					return;
				}
				fail("Should have got an BadRequestException, but didn't");
			});
	}

	// 5. Only one stream type
	@Test
	public void testGetEffortStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamType.DISTANCE.toString(), null, null);
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());
			validateArray(streams);
		});
	}

	/**
	 * Test method for
	 * {@link javastrava.api.v3.service.impl.StreamServiceImpl#getEffortStreams(java.lang.String, javastrava.api.v3.model.reference.StravaStreamType[], javastrava.api.v3.model.reference.StravaStreamResolutionType, javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType)}
	 * .
	 *
	 * @throws Exception
	 */
	// 1. Valid effort for the authenticated user
	@Test
	public void testGetEffortStreams_validEffortAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), null, null);
			validateArray(streams);
		});
	}

	// 3. Valid effort for other user
	@Test
	public void testGetEffortStreams_validEffortUnauthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreams(TestUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID, getAllStreamTypes(), null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned effort streams for a private effort belonging to another user");
		});
	}

	@Test
	public void testGetEffortStreams_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreams(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID, getAllStreamTypes(), null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned effort streams for a private activity, but without view_private");
		});
	}

	@Test
	public void testGetEffortStreams_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner
		.run(() -> {
			final StravaStream[] streams = apiWithViewPrivate().getEffortStreams(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID, getAllStreamTypes(),
					null, null);
			assertNotNull(streams);
			assertFalse(streams.length == 0);
		});
	}

	@Test
	public void testGetEffortStreams_privateSegmentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO Workaround for issue javastravav3api#87
			if (new Issue87().isIssue()) {
				return;
			}
			// End of workaround

			final StravaStream[] streams = api().getEffortStreams(TestUtils.SEGMENT_EFFORT_PRIVATE_ID, getAllStreamTypes(), null, null);
			assertNotNull(streams);
			assertTrue(streams.length == 0);
		});
	}

	@Test
	public void testGetEffortStreams_privateSegmentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = apiWithViewPrivate().getEffortStreams(TestUtils.SEGMENT_EFFORT_PRIVATE_ID, getAllStreamTypes(), null, null);
			assertNotNull(streams);
			assertFalse(streams.length == 0);
		});
	}

	private void validateArray(final StravaStream[] streams) {
		for (final StravaStream stream : streams) {
			StravaStreamTest.validate(stream);
		}
	}

	/**
	 * @return List of all valid stream types that can be requested
	 */
	private static String getAllStreamTypes() {
		final StravaStreamType[] types = StravaStreamType.values();
		String list = "";

		for (final StravaStreamType type : types) {
			if (type != StravaStreamType.UNKNOWN) {
				list = list + type.getValue() + ",";
			}
		}
		return list;
	}

}
