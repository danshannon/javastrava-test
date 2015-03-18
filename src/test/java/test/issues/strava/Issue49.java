package test.issues.strava;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.rest.ActivityAPI;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.StravaInternalServerErrorException;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if issue javastrava-api #49 is still an issue
 * </p>
 * 
 * @see <a
 *      href="https://github.com/danshannon/javastravav3api/issues/49">https://github.com/danshannon/javastravav3api/issues/49</a>
 * @author Dan Shannon
 *
 */
public class Issue49 {
	@Test
	public void testIssue() {
		ActivityAPI retrofit = API.instance(ActivityAPI.class, TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setType(StravaActivityType.UNKNOWN);
		try {
			retrofit.createManualActivity(activity);
		} catch (StravaInternalServerErrorException e) {
			// Expected
			return;
		} catch (BadRequestException e) {
			fail("Issue 49 appears to be fixed - now throwing 400 Bad Request Exception");
		}
		fail("Didn't throw the expected 500 Internal Server Error");
	}
}
