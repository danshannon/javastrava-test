package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import test.api.rest.activity.GetActivityTest;
import test.api.rest.callback.TestGetCallback;

/**
 * <p>
 * Specific tests for {@link API#getActivityAsync(Long, Boolean)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetActivityAsyncTest extends GetActivityTest {

	@Override
	protected TestGetCallback<StravaActivity, Long> getter() {
		return ((api, id) -> api.getActivityAsync(id, Boolean.FALSE).get());
	}
}
