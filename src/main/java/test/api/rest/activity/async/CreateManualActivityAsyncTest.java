package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import test.api.rest.activity.CreateManualActivityTest;
import test.api.rest.callback.TestCreateCallback;

/**
 * <p>
 * Specific tests for {@link API#createManualActivityAsync(StravaActivity)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class CreateManualActivityAsyncTest extends CreateManualActivityTest {
	@Override
	protected TestCreateCallback<StravaActivity, Integer> creator() {
		return ((api, activity, id) -> api.createManualActivityAsync(activity).get());
	}
}
