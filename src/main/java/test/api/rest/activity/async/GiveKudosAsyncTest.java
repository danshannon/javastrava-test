package test.api.rest.activity.async;

import test.api.rest.activity.GiveKudosTest;

public class GiveKudosAsyncTest extends GiveKudosTest {
	/**
	 * Override the constructor so that the callback uses the asynchronous
	 * method instead
	 */
	public GiveKudosAsyncTest() {
		super();
		this.creationCallback = (api, response, activityId) -> api.giveKudosAsync(activityId).get();
		this.createAPIResponseIsNull = true;
	}

}
