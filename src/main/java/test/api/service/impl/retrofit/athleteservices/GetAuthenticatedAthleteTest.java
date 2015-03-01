package test.api.service.impl.retrofit.athleteservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.AthleteServices;
import javastrava.api.v3.service.impl.retrofit.AthleteServicesImpl;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.utils.TestUtils;

public class GetAuthenticatedAthleteTest {
	@Test
	public void testGetAuthenticatedAthlete() {
		final StravaAthlete athlete = service().getAuthenticatedAthlete();
		StravaAthleteTest.validateAthlete(athlete,TestUtils.ATHLETE_AUTHENTICATED_ID,StravaResourceState.DETAILED);

	}

	@Test
	public void testGetAthlete_validAthlete() {
		final StravaAthlete athlete = service().getAthlete(TestUtils.ATHLETE_VALID_ID);
		StravaAthleteTest.validateAthlete(athlete,TestUtils.ATHLETE_VALID_ID,StravaResourceState.SUMMARY);
	}

	@Test
	public void testGetAthlete_invalidAthlete() {
		final StravaAthlete athlete = service().getAthlete(TestUtils.ATHLETE_INVALID_ID);
		assertNull(athlete);

	}

	@Test
	public void testGetAthlete_privateAthlete() {
		final StravaAthlete athlete = service().getAthlete(TestUtils.ATHLETE_PRIVATE_ID);
		assertNotNull(athlete);
		assertEquals(TestUtils.ATHLETE_PRIVATE_ID, athlete.getId());
		StravaAthleteTest.validateAthlete(athlete,TestUtils.ATHLETE_PRIVATE_ID,StravaResourceState.SUMMARY);
	}

	private AthleteServices service() {
		return AthleteServicesImpl.implementation(TestUtils.getValidToken());
	}
}
