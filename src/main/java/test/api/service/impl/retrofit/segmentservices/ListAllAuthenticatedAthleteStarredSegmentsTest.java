package test.api.service.impl.retrofit.segmentservices;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.service.SegmentServices;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;

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
	
	private SegmentServices service() {
		return SegmentServicesImpl.implementation(TestUtils.getValidToken());
	}

}
