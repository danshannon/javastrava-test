package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaResponse;
import test.api.rest.activity.GiveKudosTest;
import test.api.rest.callback.TestCreateCallback;

public class GiveKudosAsyncTest extends GiveKudosTest {
	@Override
	protected TestCreateCallback<StravaResponse, Long> creator() {
		return ((api, response, activityId) -> api.giveKudosAsync(activityId).get());
	}

}
