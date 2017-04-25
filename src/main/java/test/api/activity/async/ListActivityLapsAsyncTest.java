package test.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaLap;
import test.api.activity.ListActivityLapsTest;
import test.api.callback.APIListCallback;

/**
 * <p>
 * Specific tests for {@link API#listActivityLapsAsync(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityLapsAsyncTest extends ListActivityLapsTest {
	/**
	 * @see test.api.activity.ListActivityLapsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaLap, Long> listCallback() {
		return (api, id) -> api.listActivityLapsAsync(id).get();
	}
}
