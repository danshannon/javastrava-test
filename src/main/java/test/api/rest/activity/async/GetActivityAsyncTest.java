package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import test.api.rest.TestGetCallback;
import test.api.rest.activity.GetActivityTest;

public class GetActivityAsyncTest extends GetActivityTest {

	@Override
	protected TestGetCallback<StravaActivity, Long> getCallback() {
		return ((api, id) -> api.getActivityAsync(id, Boolean.FALSE).get());
	}
}
