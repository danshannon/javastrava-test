package test.api.rest.activity.async;

import test.api.rest.activity.GetActivityTest;

public class GetActivityAsyncTest extends GetActivityTest {
	/**
	 * Overriding the constructor forces use of async API
	 */
	public GetActivityAsyncTest() {
		super();
		this.getCallback = (api, id) -> api.getActivityAsync(id, null).get();
	}
}
