package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import test.api.rest.activity.DeleteActivityTest;
import test.api.rest.callback.TestDeleteCallback;

/**
 * <p>
 * Tests for {@link API#deleteActivityAsync(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class DeleteActivityAsyncTest extends DeleteActivityTest {

	@Override
	protected TestDeleteCallback<StravaActivity, Long> deleter() {
		return ((api, activity, id) -> api.deleteActivityAsync(id).get());
	}
}
