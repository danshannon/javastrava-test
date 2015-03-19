package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.service.StravaTest;

public class ListAllAuthenticatedAthleteStarredSegmentsTest extends StravaTest {
	@Test
	public void testListAllAuthenticatedAthleteStarredSegments() {
		List<StravaSegment> segments = service().listAllAuthenticatedAthleteStarredSegments();
		assertNotNull(segments);
		for (StravaSegment segment : segments) {
			StravaSegmentTest.validateSegment(segment);
		}
	}
}
