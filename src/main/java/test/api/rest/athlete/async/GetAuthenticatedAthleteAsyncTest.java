package test.api.rest.athlete.async;

import javastrava.api.v3.model.StravaAthlete;
import test.api.rest.TestGetCallback;
import test.api.rest.athlete.GetAuthenticatedAthleteTest;

public class GetAuthenticatedAthleteAsyncTest extends GetAuthenticatedAthleteTest {

	@Override
	protected TestGetCallback<StravaAthlete, Integer> getCallback() {
		return ((api, id) -> api.getAuthenticatedAthleteAsync().get());
	}
}
