package test.issues.strava;

import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.impl.retrofit.Retrofit;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesRetrofit;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if issue <a href="https://github.com/danshannon/javastravav3api/issues/23">javastrava-api #23</a> is still
 * current
 * </p>
 * 
 * @author Dan Shannon
 * @see <a
 *      href="https://github.com/danshannon/javastravav3api/issues/23">https://github.com/danshannon/javastravav3api/issues/23</a>
 */
public class Issue23 {
	@Test
	public void testIssue() throws NotFoundException {
		SegmentServicesRetrofit retrofit = Retrofit.retrofit(SegmentServicesRetrofit.class, TestUtils.getValidToken());
		retrofit.getSegmentLeaderboard(966356, null, null, null, null, 2, null, null, null, null);
		// Note - that's all for this test, because it should have thrown a NotFoundException

	}
}
