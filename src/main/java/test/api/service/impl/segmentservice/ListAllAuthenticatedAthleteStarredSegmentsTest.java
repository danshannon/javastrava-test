package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertNotNull;

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
}
