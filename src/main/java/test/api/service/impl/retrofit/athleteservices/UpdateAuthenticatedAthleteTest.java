package test.api.service.impl.retrofit.athleteservices;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.AthleteServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.AthleteServicesImpl;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.utils.TestUtils;

public class UpdateAuthenticatedAthleteTest {
	@Test
	public void testUpdateAuthenticatedAthlete() {
		final StravaAthlete athlete = service().getAuthenticatedAthlete();

		final String city = athlete.getCity();
		final String state = athlete.getState();
		final StravaGender sex = athlete.getSex();
		final String country = athlete.getCountry();
		athlete.setWeight(92.0f);
		StravaAthlete returnedAthlete = service().updateAuthenticatedAthlete(null, null, null, null, new Float(92));
		StravaAthleteTest.validateAthlete(returnedAthlete,athlete.getId(),StravaResourceState.DETAILED);
		returnedAthlete = service().updateAuthenticatedAthlete(city, state, country, sex, null);
		StravaAthleteTest.validateAthlete(returnedAthlete,athlete.getId(),StravaResourceState.DETAILED);
	}

	@Test
	public void testUpdateAuthenticatedAthlete_noWriteAccess() {
		@SuppressWarnings("unused")
		StravaAthlete athlete = serviceWithoutWriteAccess().getAuthenticatedAthlete();

		try {
			athlete = serviceWithoutWriteAccess().updateAuthenticatedAthlete(null, null, null, null, new Float(92));
		} catch (final UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Succesfully updated authenticated athlete despite not having write access");

	}

	private AthleteServices service() {
		return AthleteServicesImpl.implementation(TestUtils.getValidToken());
	}

	private AthleteServices serviceWithoutWriteAccess() {
		return AthleteServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
	}

}
