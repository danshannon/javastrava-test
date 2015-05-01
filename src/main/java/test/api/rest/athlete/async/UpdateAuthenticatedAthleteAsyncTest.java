package test.api.rest.athlete.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaAthleteTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;

public class UpdateAuthenticatedAthleteAsyncTest extends APITest<StravaAthlete> {
	@Test
	public void testUpdateAuthenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = api().getAuthenticatedAthleteAsync().get();

			final String city = athlete.getCity();
			final String state = athlete.getState();
			final StravaGender sex = athlete.getSex();
			final String country = athlete.getCountry();
			athlete.setWeight(92.0f);
			StravaAthlete returnedAthlete = apiWithWriteAccess()
					.updateAuthenticatedAthleteAsync(null, null, null, null, new Float(92)).get();
			StravaAthleteTest.validateAthlete(returnedAthlete, athlete.getId(), StravaResourceState.DETAILED);
			returnedAthlete = apiWithWriteAccess().updateAuthenticatedAthleteAsync(city, state, country, sex, null)
					.get();
			assertEquals(athlete.getWeight(), returnedAthlete.getWeight());
			StravaAthleteTest.validateAthlete(returnedAthlete, athlete.getId(), StravaResourceState.DETAILED);
		} );
	}

	@Test
	public void testUpdateAuthenticatedAthlete_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().updateAuthenticatedAthleteAsync(null, null, null, null, new Float(92)).get();
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Succesfully updated authenticated athlete despite not having write access");
		} );
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaAthlete result) throws Exception {
		StravaAthleteTest.validateAthlete(result);

	}

}
