package test.api.service.impl.athleteservice;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetAuthenticatedAthleteTest extends StravaTest {
	@Test
	public void testGetAuthenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = strava().getAuthenticatedAthlete();
			StravaAthleteTest.validateAthlete(athlete, TestUtils.ATHLETE_AUTHENTICATED_ID, StravaResourceState.DETAILED);
		});
	}

}
