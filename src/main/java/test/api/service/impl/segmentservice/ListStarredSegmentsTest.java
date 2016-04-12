package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.service.standardtests.PagingListMethodTest;
import test.api.service.standardtests.callbacks.PagingListCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListStarredSegmentsTest extends PagingListMethodTest<StravaSegment, Integer> {
	@Override
	protected PagingListCallback<StravaSegment> callback() {
		return (paging -> strava().listStarredSegments(TestUtils.ATHLETE_AUTHENTICATED_ID, paging));
	}

	@Test
	public void testListStarredSegments_authenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listStarredSegments(TestUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(segments);
		});
	}

	@Test
	public void testListStarredSegments_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listStarredSegments(TestUtils.ATHLETE_INVALID_ID);
			assertNull(segments);
		});
	}

	@Test
	public void testListStarredSegments_otherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listStarredSegments(TestUtils.ATHLETE_VALID_ID);
			assertNotNull(segments);
		});
	}

	@Test
	public void testListStarredSegments_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listStarredSegments(TestUtils.ATHLETE_PRIVATE_ID);
			assertNotNull(segments);
			assertEquals(0, segments.size());
		});
	}

	@Override
	protected void validate(final StravaSegment segment) {
		StravaSegmentTest.validateSegment(segment);
	}

	@Override
	protected void validate(final StravaSegment segment, final Integer id, final StravaResourceState state) {
		StravaSegmentTest.validateSegment(segment, id, state);

	}

}
