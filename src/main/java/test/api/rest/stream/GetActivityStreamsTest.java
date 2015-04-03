package test.api.rest.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import test.issues.strava.Issue21;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetActivityStreamsTest extends APITest {
	// 4. All stream types
	@Test
	public void testGetActivityStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), null, null);
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
	@Test
	public void testGetActivityStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), resolutionType,
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

	// 6. Downsampled by time
	@Test
	public void testGetActivityStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					final StravaStream[] streams = api().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), resolutionType,
							StravaStreamSeriesDownsamplingType.TIME);
					assertNotNull(streams);
					validateArray(streams);
				}
			}
		});
	}

	// 2. Invalid activity
	@Test
	public void testGetActivityStreams_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreams(TestUtils.ACTIVITY_INVALID, getAllStreamTypes(), null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got activity stream for a non-existent activity");
		});
	}

	// 9. Invalid downsample resolution
	@Test
	public void testGetActivityStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution");
		});
	}

	// 10. Invalid downsample type (i.e. not distance or time)
	@Test
	public void testGetActivityStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), StravaStreamResolutionType.LOW,
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
	public void testGetActivityStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamType.UNKNOWN.getValue(), null, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have thrown a bad request exception");
		});
	}

	// 5. Only one stream type
	@Test
	public void testGetActivityStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api()
					.getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaStreamType.DISTANCE.toString(), null, null);
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
	// 1. Valid activity for the authenticated user
	@Test
	public void testGetActivityStreams_validActivityAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, getAllStreamTypes(), null, null);
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
	@Test
	public void testGetActivityStreams_validActivityUnauthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreams(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, getAllStreamTypes(), null, null);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Shouldn't be able to return activity streams for activities that don't belong to the authenticated user");
		});
	}

	@Test
	public void testGetActivityStreams_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getActivityStreams(TestUtils.ACTIVITY_PRIVATE, getAllStreamTypes(), null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Got activity streams for a private activity, but without view_private access");
		});
	}

	@Test
	public void testGetActivityStreams_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = apiWithViewPrivate().getActivityStreams(TestUtils.ACTIVITY_PRIVATE, getAllStreamTypes(), null, null);
			assertNotNull(streams);
			assertFalse(streams.length == 0);
		});
	}

	private void validateArray(final StravaStream[] streams) {
		assertNotNull(streams);
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
