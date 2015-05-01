package test.api.rest.activity.async;

import test.api.rest.activity.CreateManualActivityTest;

public class CreateManualActivityAsyncTest extends CreateManualActivityTest {
	/**
	 * No argument constructor sets up the callback to use to create the manual activity
	 */
	public CreateManualActivityAsyncTest() {
		super();
		this.creationCallback = (api, activity, id) -> api.createManualActivityAsync(activity).get();
	}
}
