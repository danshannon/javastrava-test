package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.service.StravaTest;
import test.utils.TestUtils;

public class ListAllStarredSegmentsTest extends StravaTest {
	@Test
	public void listAllStarredSegments_validAthlete() {
		List<StravaSegment> segments = service().listAllStarredSegments(TestUtils.ATHLETE_VALID_ID);
		assertNotNull(segments);
		for (StravaSegment segment : segments) {
			StravaSegmentTest.validateSegment(segment);
		}
	}
	
	@Test
	public void listAllStarredSegments_invalidAthlete() {
		List<StravaSegment> segments = service().listAllStarredSegments(TestUtils.ATHLETE_INVALID_ID);
		assertNull(segments);
	}
	
	@Test
	public void listAllStarredSegments_authenticatedAthlete() {
		List<StravaSegment> segments = service().listAllStarredSegments(TestUtils.ATHLETE_AUTHENTICATED_ID);
		assertNotNull(segments);
		
		List<StravaSegment> starredSegments = service().listAllAuthenticatedAthleteStarredSegments();
		assertNotNull(starredSegments);
		assertEquals(starredSegments.size(),segments.size());

	}

}
