package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;

public class UpdateAuthenticatedAthleteTest extends StravaTest {
	@Test
	public void testUpdateAuthenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = strava().getAuthenticatedAthlete();

			final String city = athlete.getCity();
			final String state = athlete.getState();
			final StravaGender sex = athlete.getSex();
			final String country = athlete.getCountry();
			athlete.setWeight(92.0f);
			StravaAthlete returnedAthlete = stravaWithWriteAccess().updateAuthenticatedAthlete(null, null, null, null,
					new Float(92));
			StravaAthleteTest.validateAthlete(returnedAthlete, athlete.getId(), StravaResourceState.DETAILED);
			returnedAthlete = stravaWithWriteAccess().updateAuthenticatedAthlete(city, state, country, sex, null);
			assertEquals(athlete.getWeight(), returnedAthlete.getWeight());
			StravaAthleteTest.validateAthlete(returnedAthlete, athlete.getId(), StravaResourceState.DETAILED);
		});
	}

	@Test
	public void testUpdateAuthenticatedAthlete_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				strava().updateAuthenticatedAthlete(null, null, null, null, new Float(92));
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Succesfully updated authenticated athlete despite not having write access");
		});
	}

}
