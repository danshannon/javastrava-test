package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import test.api.rest.TestCreateCallback;
import test.api.rest.activity.CreateManualActivityTest;

public class CreateManualActivityAsyncTest extends CreateManualActivityTest {
	@Override
	protected TestCreateCallback<StravaActivity, Integer> creator() {
		return ((api, activity, id) -> api.createManualActivityAsync(activity).get());
	}
}
