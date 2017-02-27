package test.api.rest.activity.async;

import test.api.rest.activity.DeleteActivityTest;

public class DeleteActivityAsyncTest extends DeleteActivityTest {
	/**
	 *
	 */
	public DeleteActivityAsyncTest() {
		super();

		this.callback = (api, activity, activityId) -> api.deleteActivityAsync(activityId).get();
	}

}
