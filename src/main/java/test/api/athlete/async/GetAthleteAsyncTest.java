package test.api.athlete.async;

import javastrava.api.API;
import javastrava.model.StravaAthlete;
import test.api.athlete.GetAthleteTest;
import test.api.callback.APIGetCallback;
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
