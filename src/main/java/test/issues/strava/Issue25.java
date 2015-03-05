package test.issues.strava;

import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.impl.retrofit.Retrofit;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesRetrofit;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if issue <a href="https://github.com/danshannon/javastravav3api/issues/25">javastrava-api #25</a> is still an issue
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/25">https://github.com/danshannon/javastravav3api/issues/25</a>
 *
 */
public class Issue25 {
	@Test
	public void testIssue_listAthleteStarredSegments() throws NotFoundException {
		SegmentServicesRetrofit retrofit = Retrofit.retrofit(SegmentServicesRetrofit.class, TestUtils.getValidToken());
		StravaSegment[] segments = retrofit.listStarredSegments(5614, 1, 2);
		boolean issue = false;
		for (StravaSegment segment : segments) {
			if (segment.getAthletePrEffort() != null && segment.getAthletePrEffort().getResourceState() == null) {
				issue = true;
			}
		}
		assertTrue(issue);
	}
	
	@Test
	public void testIssue_listAuthenticatedAthleteStarredSegments() {
		SegmentServicesRetrofit retrofit = Retrofit.retrofit(SegmentServicesRetrofit.class, TestUtils.getValidToken());
		StravaSegment[] segments = retrofit.listAuthenticatedAthleteStarredSegments(1, 50);
		boolean issue = false;
		for (StravaSegment segment : segments) {
			if (segment.getAthletePrEffort() != null && segment.getAthletePrEffort().getResourceState() == null) {
				issue = true;
			}
		}
		assertTrue(issue);
	
	}
}
