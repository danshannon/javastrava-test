package test.issues.strava;

import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesRetrofit;
import javastrava.api.v3.service.impl.retrofit.Retrofit;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if <a href="https://github.com/danshannon/javastravav3api/issues/29">javastrava-api #29</a> remains an issue
 * </p>
 * 
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/29">https://github.com/danshannon/javastravav3api/issues/29</a>
 *
 */
public class Issue29 {
	@Test
	public void testIssue() throws NotFoundException {
		ActivityServicesRetrofit retrofit = Retrofit.retrofit(ActivityServicesRetrofit.class,TestUtils.getValidTokenWithoutWriteAccess());
		retrofit.giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
	}

}
