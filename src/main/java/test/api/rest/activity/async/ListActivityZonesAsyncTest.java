package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivityZone;
import javastrava.api.v3.rest.API;
import test.api.rest.activity.ListActivityZonesTest;
import test.api.rest.callback.TestListArrayCallback;

/**
 * <p>
 * Specific tests for {@link API#listActivityZonesAsync(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityZonesAsyncTest extends ListActivityZonesTest {
	/**
	 * @see test.api.rest.activity.ListActivityZonesTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaActivityZone, Long> listCallback() {
		return (api, id) -> api.listActivityZonesAsync(id).get();
	}
}
