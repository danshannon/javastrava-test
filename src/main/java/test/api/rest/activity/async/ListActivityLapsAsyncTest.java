package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaLap;
import javastrava.api.v3.rest.API;
import test.api.rest.activity.ListActivityLapsTest;
import test.api.rest.callback.APIListCallback;

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
	 * @see test.api.rest.activity.ListActivityLapsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaLap, Long> listCallback() {
		return (api, id) -> api.listActivityLapsAsync(id).get();
	}
}
