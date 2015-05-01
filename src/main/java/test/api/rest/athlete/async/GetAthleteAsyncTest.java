package test.api.rest.athlete.async;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.api.rest.athlete.GetAthleteTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetAthleteAsyncTest extends GetAthleteTest {
	/**
	 *
	 */
	public GetAthleteAsyncTest() {
		this.getCallback = (api, id) -> api.getAthleteAsync(id).get();
	}

	@Override
	@Test
	public void testGetAthlete_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = api().getAthleteAsync(TestUtils.ATHLETE_PRIVATE_ID).get();
			StravaAthleteTest.validateAthlete(athlete);
		} );
	}

}
