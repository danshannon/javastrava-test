package test.issues.strava;

import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.ActivityAPI;
import javastrava.api.v3.service.exception.NotFoundException;

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
		final ActivityAPI retrofit = API.instance(ActivityAPI.class, TestUtils.getValidToken());
		retrofit.giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
	}

}
