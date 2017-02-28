package test.service.impl.athleteservice;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaAthleteTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetAuthenticatedAthleteTest {
	@Test
	public void testGetAuthenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = TestUtils.strava().getAuthenticatedAthlete();
			StravaAthleteTest.validateAthlete(athlete, TestUtils.ATHLETE_AUTHENTICATED_ID, StravaResourceState.DETAILED);
		});
	}

}
