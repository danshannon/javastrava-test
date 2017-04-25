package test.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaActivity;
import test.api.activity.CreateManualActivityTest;
import test.api.callback.APICreateCallback;

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
	protected APICreateCallback<StravaActivity, Integer> creator() {
		return ((api, activity, id) -> api.createManualActivityAsync(activity).get());
	}
}
