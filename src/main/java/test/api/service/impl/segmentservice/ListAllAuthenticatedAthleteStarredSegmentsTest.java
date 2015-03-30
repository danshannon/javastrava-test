package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;

public class ListAllAuthenticatedAthleteStarredSegmentsTest extends StravaTest {
	@Test
	public void testListAllAuthenticatedAthleteStarredSegments() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listAllAuthenticatedAthleteStarredSegments();
			assertNotNull(segments);
			for (final StravaSegment segment : segments) {
				StravaSegmentTest.validateSegment(segment);
			}
		});
	}

	@Test
	public void testListAllAuthenticatedAthleteStarredSegments_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listAllAuthenticatedAthleteStarredSegments();
			for (final StravaSegment segment : segments) {
				if ((segment.getPrivateSegment() != null) && segment.getPrivateSegment().equals(Boolean.TRUE)) {
					fail("Returned at least one private starred segment");
				}
			}
		});
	}

	@Test
	public void testListAllAuthenticatedAthleteStarredSegments_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = stravaWithViewPrivate().listAllAuthenticatedAthleteStarredSegments();
			boolean pass = false;
			for (final StravaSegment segment : segments) {
				if (segment.getPrivateSegment().equals(Boolean.TRUE)) {
					pass = true;
				}
			}
			assertTrue(pass);
		});
	}
}
