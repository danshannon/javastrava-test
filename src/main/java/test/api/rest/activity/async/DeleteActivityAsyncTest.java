package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import test.api.rest.TestDeleteCallback;
import test.api.rest.activity.DeleteActivityTest;

public class DeleteActivityAsyncTest extends DeleteActivityTest {
	/**
	 *
	 */
	public DeleteActivityAsyncTest() {
		super();

		this.callback = new TestDeleteCallback<StravaActivity, Integer>() {
			@Override
			public StravaActivity run(final API api, final StravaActivity activity, final Integer activityId) throws Exception {
				return api.deleteActivityAsync(activityId).get();
			}
		};
	}

}
