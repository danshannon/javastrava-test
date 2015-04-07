package test.api.rest.athlete;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetAthleteTest extends APITest {
	@Test
	public void testGetAthlete_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getAthlete(TestUtils.ATHLETE_INVALID_ID);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned an invalid athlete!");
		});
	}

	@Test
	public void testGetAthlete_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = api().getAthlete(TestUtils.ATHLETE_PRIVATE_ID);
			StravaAthleteTest.validateAthlete(athlete);
		});
	}

	@Test
	public void testGetAthlete_validAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = api().getAthlete(TestUtils.ATHLETE_VALID_ID);
			StravaAthleteTest.validateAthlete(athlete, TestUtils.ATHLETE_VALID_ID, StravaResourceState.SUMMARY);
		});
	}

}
