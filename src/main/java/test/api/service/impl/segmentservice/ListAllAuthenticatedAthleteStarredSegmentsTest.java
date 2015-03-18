package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.service.SegmentService;
import javastrava.api.v3.service.impl.SegmentServiceImpl;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.utils.TestUtils;

public class ListAllAuthenticatedAthleteStarredSegmentsTest {
	@Test
	public void testListAllAuthenticatedAthleteStarredSegments() {
		List<StravaSegment> segments = service().listAllAuthenticatedAthleteStarredSegments();
		assertNotNull(segments);
		for (StravaSegment segment : segments) {
			StravaSegmentTest.validateSegment(segment);
		}
	}
	
	private SegmentService service() {
		return SegmentServiceImpl.instance(TestUtils.getValidToken());
	}

}
