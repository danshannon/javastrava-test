package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import test.api.rest.activity.DeleteActivityTest;
import test.api.rest.callback.APIDeleteCallback;

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
	protected APIDeleteCallback<StravaActivity> deleter() {
		return ((api, activity) -> api.deleteActivityAsync(activity.getId()).get());
	}

	@Override
	protected String classUnderTest() {
		return this.getClass().getName();
	}
}
