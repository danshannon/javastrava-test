package test.issues.strava;

import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.impl.retrofit.Retrofit;
import javastrava.api.v3.service.impl.retrofit.SegmentEffortServicesRetrofit;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * These tests should PASS if issue <a href="https://github.com/danshannon/javastravav3api/issues/27">javastrava-api #27</a> still exists
 * </p>
 * 
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/27">https://github.com/danshannon/javastravav3api/issues/27</a>
 *
 */
public class Issue27 {
	@Test
	public void testIssue() throws NotFoundException {
		SegmentEffortServicesRetrofit retrofit = Retrofit.retrofit(SegmentEffortServicesRetrofit.class, TestUtils.getValidToken());
		StravaSegmentEffort effort = retrofit.getSegmentEffort(5591276015L);
		assertNull(effort.getAthlete().getResourceState());
	}
}
