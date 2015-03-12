package test.issues.strava;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.impl.retrofit.Retrofit;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesRetrofit;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if issue <a href="https://github.com/danshannon/javastravav3api/issues/19">javastrava-api #19</a> is still current
 * </p>
 * 
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/19">https://github.com/danshannon/javastravav3api/issues/19</a>
 *
 */
public class Issue19 {
	@Test
	public void testIssue() throws NotFoundException {
		SegmentServicesRetrofit retrofit = Retrofit.retrofit(SegmentServicesRetrofit.class, TestUtils.getValidToken());
		List<StravaSegmentEffort> efforts = Arrays.asList(retrofit.listSegmentEfforts(966356, null, null, null, null, null));
		boolean issue = false;
		for (StravaSegmentEffort effort : efforts) {
			if (effort.getActivity().getResourceState() == null) {
				issue = true;
			}
		}
		assertTrue(issue);
	}
}
