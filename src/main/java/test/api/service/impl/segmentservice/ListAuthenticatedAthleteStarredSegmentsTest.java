package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;

public class ListAuthenticatedAthleteStarredSegmentsTest extends PagingListMethodTest<StravaSegment, Integer> {
	@Override
	protected ListCallback<StravaSegment> callback() {
		return (paging -> strava().listAuthenticatedAthleteStarredSegments(paging));
	}

	// Test cases:
	// 1. No paging
	@Test
	public void testListAuthenticatedAthleteStarredSegments_noPaging() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listAuthenticatedAthleteStarredSegments();
			assertNotNull(segments);
			assertFalse(segments.size() == 0);
			validateList(segments);
		});
	}

	@Test
	public void testListAuthenticatedAthleteStarredSegments_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listAuthenticatedAthleteStarredSegments();
			for (final StravaSegment segment : segments) {
				if ((segment.getPrivateSegment() != null) && segment.getPrivateSegment().equals(Boolean.TRUE)) {
					fail("Returned at least one private starred segment");
				}
			}
		});
	}

	@Test
	public void testListAuthenticatedAthleteStarredSegments_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listAuthenticatedAthleteStarredSegments();
			boolean pass = false;
			for (final StravaSegment segment : segments) {
				if (segment.getResourceState() == StravaResourceState.PRIVATE) {
					pass = true;
				}
			}
			assertTrue(pass);
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
