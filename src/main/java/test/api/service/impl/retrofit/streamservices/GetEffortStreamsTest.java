package test.api.service.impl.retrofit.streamservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.service.StreamServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.StreamServicesImpl;

import org.junit.Test;

import test.api.model.StravaStreamTest;
import test.utils.TestUtils;

public class GetEffortStreamsTest {
	/**
	 * Test method for
	 * {@link javastrava.api.v3.service.impl.retrofit.StreamServicesImpl#getEffortStreams(java.lang.String, javastrava.api.v3.model.reference.StravaStreamType[], javastrava.api.v3.model.reference.StravaStreamResolutionType, javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType)}
	 * .
	 */
	// 1. Valid effort for the authenticated user
	@Test
	public void testGetEffortStreams_validEffortAuthenticatedUser() throws UnauthorizedException {
		final StreamServices service = getService();
		final List<StravaStream> streams = service.getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID);
		validateList(streams);
	}

	// 2. Invalid effort
	@Test
	public void testGetEffortStreams_invalidEffort() throws UnauthorizedException {
		final StreamServices service = getService();
		final List<StravaStream> streams = service.getEffortStreams(TestUtils.SEGMENT_EFFORT_INVALID_ID);
		assertNull(streams);
	}

	// 3. Valid effort for other user
	@Test
	public void testGetEffortStreams_validEffortUnauthenticatedUser() {
		final StreamServices service = getService();
		try {
			service.getEffortStreams(TestUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID);
		} catch (final UnauthorizedException e) {
			// Expected
			return;
		}
		fail("Shouldn't be able to return effort streams for activities that don't belong to the authenticated user");
	}

	// 4. All stream types
	@Test
	public void testGetEffortStreams_allStreamTypes() throws UnauthorizedException {
		final StreamServices service = getService();
		final List<StravaStream> streams = service.getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID);
		validateList(streams);

	}

	// 5. Only one stream type
	@Test
	public void testGetEffortStreams_oneStreamType() throws UnauthorizedException {
		final StreamServices service = getService();
		final List<StravaStream> streams = service.getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, null, null, StravaStreamType.DISTANCE);
		assertNotNull(streams);
		assertEquals(1, streams.size());
		assertEquals(StravaStreamType.DISTANCE, streams.get(0).getType());
		validateList(streams);
	}

	// 6. Downsampled by time
	@Test
	public void testGetEffortStreams_downsampledByTime() throws UnauthorizedException {
		final StreamServices service = getService();
		for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
			if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
				final List<StravaStream> streams = service.getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, resolutionType,
						StravaStreamSeriesDownsamplingType.TIME);
				validateList(streams);
			}
		}
	}

	// 7. Downsampled by distance
	@Test
	public void testGetEffortStreams_downsampledByDistance() throws UnauthorizedException {
		final StreamServices service = getService();
		for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
			if (resolutionType != StravaStreamResolutionType.UNKNOWN && resolutionType != null) {
				final List<StravaStream> streams = service.getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, resolutionType,
						StravaStreamSeriesDownsamplingType.DISTANCE);
				validateList(streams);
			}
		}
	}

	// 8. Invalid stream type
	@Test
	public void testGetEffortStreams_invalidStreamType() throws UnauthorizedException {
		final StreamServices service = getService();
		try {
			service.getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, null, null, StravaStreamType.UNKNOWN);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Should have got an IllegalArgumentException, but didn't");
	}

	// 9. Invalid downsample resolution
	@Test
	public void testGetEffortStreams_invalidDownsampleResolution() throws UnauthorizedException {
		final StreamServices service = getService();
		try {
			service.getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamResolutionType.UNKNOWN, null);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Didn't throw an exception when asking for an invalid downsample resolution");
	}

	// 10. Invalid downsample type (i.e. not distance or time)
	@Test
	public void testGetEffortStreams_invalidDownsampleType() throws UnauthorizedException {
		final StreamServices service = getService();
		try {
			service.getEffortStreams(TestUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Didn't throw an exception when asking for an invalid downsample type");
	}

	private StreamServices getService() {
		return StreamServicesImpl.implementation(TestUtils.getValidToken());
	}

	private void validateList(final List<StravaStream> streams) {
		for (final StravaStream stream : streams) {
			StravaStreamTest.validate(stream);
		}
	}

}
