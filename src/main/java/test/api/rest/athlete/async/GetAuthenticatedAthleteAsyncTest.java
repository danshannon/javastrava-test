package test.api.rest.athlete.async;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import test.api.rest.athlete.GetAuthenticatedAthleteTest;
import test.api.rest.callback.APIGetCallback;

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
