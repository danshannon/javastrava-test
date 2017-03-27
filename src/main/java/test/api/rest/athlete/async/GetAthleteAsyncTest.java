package test.api.rest.athlete.async;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import test.api.rest.athlete.GetAthleteTest;
import test.api.rest.callback.APIGetCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#getAthleteAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAthleteAsyncTest extends GetAthleteTest {
	@Override
	protected APIGetCallback<StravaAthlete, Integer> getter() {
		return ((api, id) -> api.getAthleteAsync(id).get());
	}

	@Override
	public void testGetAthlete_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = api().getAthleteAsync(AthleteDataUtils.ATHLETE_PRIVATE_ID).get();
			AthleteDataUtils.validateAthlete(athlete);
		});
	}

}
