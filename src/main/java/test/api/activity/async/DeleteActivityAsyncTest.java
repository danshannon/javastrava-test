package test.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaActivity;
import test.api.activity.DeleteActivityTest;
import test.api.callback.APIDeleteCallback;

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
	protected String classUnderTest() {
		return this.getClass().getName();
	}

	@Override
	protected APIDeleteCallback<StravaActivity> deleter() {
		return ((api, activity) -> api.deleteActivityAsync(activity.getId()).get());
	}
}
