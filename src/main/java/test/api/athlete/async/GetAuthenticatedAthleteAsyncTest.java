package test.api.athlete.async;

import javastrava.api.API;
import javastrava.model.StravaAthlete;
import test.api.athlete.GetAuthenticatedAthleteTest;
import test.api.callback.APIGetCallback;

/**
 * <p>
 * Specific tests for {@link API#getAuthenticatedAthleteAsync()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAuthenticatedAthleteAsyncTest extends GetAuthenticatedAthleteTest {

	@Override
	protected APIGetCallback<StravaAthlete, Integer> getter() {
		return ((api, id) -> api.getAuthenticatedAthleteAsync().get());
	}
}
