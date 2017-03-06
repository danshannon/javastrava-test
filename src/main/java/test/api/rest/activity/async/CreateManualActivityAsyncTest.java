package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import test.api.rest.activity.CreateManualActivityTest;
import test.api.rest.callback.TestCreateCallback;

public class CreateManualActivityAsyncTest extends CreateManualActivityTest {
	@Override
	protected TestCreateCallback<StravaActivity, Integer> creator() {
		return ((api, activity, id) -> api.createManualActivityAsync(activity).get());
	}
}
