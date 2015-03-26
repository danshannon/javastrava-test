package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetAuthenticatedAthleteTest extends StravaTest {
	@Test
	public void testGetAthlete_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = strava().getAthlete(TestUtils.ATHLETE_INVALID_ID);
			assertNull(athlete);
		});
	}

	@Test
	public void testGetAthlete_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = strava().getAthlete(TestUtils.ATHLETE_PRIVATE_ID);
			assertNotNull(athlete);
			assertEquals(TestUtils.ATHLETE_PRIVATE_ID, athlete.getId());
			StravaAthleteTest.validateAthlete(athlete, TestUtils.ATHLETE_PRIVATE_ID, StravaResourceState.SUMMARY);
		});
	}

	@Test
	public void testGetAthlete_validAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = strava().getAthlete(TestUtils.ATHLETE_VALID_ID);
			StravaAthleteTest.validateAthlete(athlete, TestUtils.ATHLETE_VALID_ID, StravaResourceState.SUMMARY);
		});
	}

	@Test
	public void testGetAuthenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = strava().getAuthenticatedAthlete();
			StravaAthleteTest.validateAthlete(athlete, TestUtils.ATHLETE_AUTHENTICATED_ID, StravaResourceState.DETAILED);
		});
	}

}
