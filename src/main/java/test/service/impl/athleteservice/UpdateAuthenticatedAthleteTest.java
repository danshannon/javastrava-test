package test.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaAthleteTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for update authenticated athlete methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class UpdateAuthenticatedAthleteTest {
	/**
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testUpdateAuthenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = TestUtils.strava().getAuthenticatedAthlete();

			final String city = athlete.getCity();
			final String state = athlete.getState();
			final StravaGender sex = athlete.getSex();
			final String country = athlete.getCountry();
			athlete.setWeight(new Float(92.0f));
			StravaAthlete returnedAthlete = TestUtils.stravaWithWriteAccess().updateAuthenticatedAthlete(null, null, null, null,
					new Float(92));
			StravaAthleteTest.validateAthlete(returnedAthlete, athlete.getId(), StravaResourceState.DETAILED);
			returnedAthlete = TestUtils.stravaWithWriteAccess().updateAuthenticatedAthlete(city, state, country, sex, null);
			assertEquals(athlete.getWeight(), returnedAthlete.getWeight());
			StravaAthleteTest.validateAthlete(returnedAthlete, athlete.getId(), StravaResourceState.DETAILED);
		});
	}

	/**
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testUpdateAuthenticatedAthlete_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.strava().updateAuthenticatedAthlete(null, null, null, null, new Float(92));
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Succesfully updated authenticated athlete despite not having write access"); //$NON-NLS-1$
		});
	}

}
