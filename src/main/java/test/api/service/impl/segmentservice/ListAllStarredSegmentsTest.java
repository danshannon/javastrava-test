package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAllStarredSegmentsTest extends StravaTest {
	@Test
	public void listAllStarredSegments_authenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listAllStarredSegments(TestUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(segments);

			final List<StravaSegment> starredSegments = strava().listAllAuthenticatedAthleteStarredSegments();
			assertNotNull(starredSegments);
			assertEquals(starredSegments.size(), segments.size());
		});
	}

	@Test
	public void listAllStarredSegments_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listAllStarredSegments(TestUtils.ATHLETE_INVALID_ID);
			assertNull(segments);
		});
	}

	@Test
	public void listAllStarredSegments_validAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = strava().listAllStarredSegments(TestUtils.ATHLETE_VALID_ID);
			assertNotNull(segments);
			for (final StravaSegment segment : segments) {
				StravaSegmentTest.validateSegment(segment);
			}
		});
	}

}
