package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import test.api.rest.activity.GetActivityTest;
import test.api.rest.callback.TestGetCallback;

public class GetActivityAsyncTest extends GetActivityTest {

	@Override
	protected TestGetCallback<StravaActivity, Long> getter() {
		return ((api, id) -> api.getActivityAsync(id, Boolean.FALSE).get());
	}
}
