package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaResponse;
import javastrava.api.v3.rest.API;
import test.api.rest.activity.GiveKudosTest;
import test.api.rest.callback.APICreateCallback;

/**
 * <p>
 * Specific tests for {@link API#giveKudosAsync(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GiveKudosAsyncTest extends GiveKudosTest {
	@Override
	protected APICreateCallback<StravaResponse, Long> creator() {
		return ((api, response, activityId) -> api.giveKudosAsync(activityId).get());
	}

}
