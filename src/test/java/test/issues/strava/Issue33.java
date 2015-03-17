package test.issues.strava;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
 * These tests will PASS if issue 33 (<a
 * href="https://github.com/danshannon/javastravav3api/issues/33">https://github.com/danshannon/javastravav3api/issues/33)</a> is
 * still an issue.
 * </p>
 */
public class Issue33 {
	/**
	 * This test will PASS if issue 33 (https://github.com/danshannon/javastravav3api/issues/33) is still an issue
	 * 
	 * @throws NotFoundException
	 *             If the segment doesn't exist
	 */
	@Test
	public void testIssue33() throws NotFoundException {
		// Create a retrofit service to use
		SegmentServicesRetrofit retrofit = Retrofit.retrofit(SegmentServicesRetrofit.class, TestUtils.getValidToken());
		List<StravaSegmentEffort> efforts = Arrays.asList(retrofit.listSegmentEfforts(1111556, null, null,
				null, null, null));
		assertNotNull(efforts);
		assertFalse(efforts.isEmpty());
	}

}
