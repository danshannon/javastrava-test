package test.api.rest.athlete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaAthleteTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#updateAuthenticatedAthlete(String, String, String, StravaGender, Float)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class UpdateAuthenticatedAthleteTest extends APITest<StravaAthlete> {
	/**
	 * Test a valid update of the athlete
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testUpdateAuthenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = api().getAuthenticatedAthlete();

			final String city = athlete.getCity();
			final String state = athlete.getState();
			final StravaGender sex = athlete.getSex();
			final String country = athlete.getCountry();
			athlete.setWeight(92.0f);
			StravaAthlete returnedAthlete = apiWithWriteAccess().updateAuthenticatedAthlete(null, null, null, null, new Float(92));
			StravaAthleteTest.validateAthlete(returnedAthlete, athlete.getId(), StravaResourceState.DETAILED);
			returnedAthlete = apiWithWriteAccess().updateAuthenticatedAthlete(city, state, country, sex, null);
			assertEquals(athlete.getWeight(), returnedAthlete.getWeight());
			StravaAthleteTest.validateAthlete(returnedAthlete, athlete.getId(), StravaResourceState.DETAILED);
		});
	}

	/**
	 * Attempt to update the authenticated athlete using a token which does not have {@link AuthorisationScope#WRITE} scope. Should
	 * fail with an {@link UnauthorizedException}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testUpdateAuthenticatedAthlete_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().updateAuthenticatedAthlete(null, null, null, null, new Float(92));
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Succesfully updated authenticated athlete despite not having write access"); //$NON-NLS-1$
		});
	}

	@Override
	protected void validate(final StravaAthlete result) throws Exception {
		StravaAthleteTest.validateAthlete(result);

	}

}
