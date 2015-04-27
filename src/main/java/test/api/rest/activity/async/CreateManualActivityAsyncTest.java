package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import test.api.rest.TestCreateCallback;
import test.api.rest.activity.CreateManualActivityTest;

public class CreateManualActivityAsyncTest extends CreateManualActivityTest {
	/**
	 * No argument constructor sets up the callback to use to create the manual activity
	 */
	public CreateManualActivityAsyncTest() {
		super();
		this.creationCallback = new TestCreateCallback<StravaActivity, Integer>() {

			@Override
			public StravaActivity run(final API api, final StravaActivity activity, final Integer id) throws Exception {
				return api.createManualActivityAsync(activity).get();
			}
		};
	}
}
