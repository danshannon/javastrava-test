package test.api.rest.athlete.async;

import javastrava.api.v3.model.StravaAthlete;
import test.api.rest.athlete.GetAuthenticatedAthleteTest;
import test.api.rest.callback.TestGetCallback;

public class GetAuthenticatedAthleteAsyncTest extends GetAuthenticatedAthleteTest {

	@Override
	protected TestGetCallback<StravaAthlete, Integer> getter() {
		return ((api, id) -> api.getAuthenticatedAthleteAsync().get());
	}
}
