package test.api.service.impl.streamservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaStreamTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class GetEffortStreamsTest extends StravaTest {
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
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaStream> streams = strava().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID);
				validateList(streams);
			}
		});
	}

	// 2. Invalid effort
	@Test
	public void testGetEffortStreams_invalidEffort() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaStream> streams = strava().getEffortStreams(TestUtils.SEGMENT_EFFORT_INVALID_ID);
				assertNull(streams);
			}
		});
	}

	// 3. Valid effort for other user
	@Test
	public void testGetEffortStreams_validEffortUnauthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				try {
					strava().getEffortStreams(TestUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID);
				} catch (final UnauthorizedException e) {
					// Expected
					return;
				}
				fail("Shouldn't be able to return effort streams for activities that don't belong to the authenticated user");
			}
		});
	}

	// 4. All stream types
	@Test
	public void testGetEffortStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaStream> streams = strava().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID);
				validateList(streams);
			}
		});
	}

	// 5. Only one stream type
	@Test
	public void testGetEffortStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaStream> streams = strava().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, null, null, StravaStreamType.DISTANCE);
				assertNotNull(streams);
				assertEquals(1, streams.size());
				assertEquals(StravaStreamType.DISTANCE, streams.get(0).getType());
				validateList(streams);
			}
		});
	}

	// 6. Downsampled by time
	@Test
	public void testGetEffortStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
					if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
						final List<StravaStream> streams = strava().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, resolutionType,
								StravaStreamSeriesDownsamplingType.TIME);
						validateList(streams);
					}
				}
			}
		});
	}

	// 7. Downsampled by distance
	@Test
	public void testGetEffortStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
					if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
						final List<StravaStream> streams = strava().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, resolutionType,
								StravaStreamSeriesDownsamplingType.DISTANCE);
						validateList(streams);
					}
				}
			}
		});
	}

	// 8. Invalid stream type
	@Test
	public void testGetEffortStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				try {
					strava().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, null, null, StravaStreamType.UNKNOWN);
				} catch (final IllegalArgumentException e) {
					// Expected
					return;
				}
				fail("Should have got an IllegalArgumentException, but didn't");
			}
		});
	}

	// 9. Invalid downsample resolution
	@Test
	public void testGetEffortStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				try {
					strava().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamResolutionType.UNKNOWN, null);
				} catch (final IllegalArgumentException e) {
					// Expected
					return;
				}
				fail("Didn't throw an exception when asking for an invalid downsample resolution");
			}
		});
	}

	// 10. Invalid downsample type (i.e. not distance or time)
	@Test
	public void testGetEffortStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				try {
					strava().getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN);
				} catch (final IllegalArgumentException e) {
					// Expected
					return;
				}
				fail("Didn't throw an exception when asking for an invalid downsample type");
			}
		});
	}

	private void validateList(final List<StravaStream> streams) {
		for (final StravaStream stream : streams) {
			StravaStreamTest.validate(stream);
		}
	}

}
