package test.service.impl.athleteservice;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.Strava;
import test.api.model.StravaAthleteTest;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests and configuration for {@link Strava#getAuthenticatedAthlete()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAuthenticatedAthleteTest {
	/**
	 * Check operation of the method to get the authenticated athlete
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetAuthenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = TestUtils.strava().getAuthenticatedAthlete();
			StravaAthleteTest.validateAthlete(athlete, AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, StravaResourceState.DETAILED);
		});
	}

}
