package test.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaActivityZone;
import test.api.activity.ListActivityZonesTest;
import test.api.callback.APIListCallback;

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
	 * @see test.api.activity.ListActivityZonesTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaActivityZone, Long> listCallback() {
		return (api, id) -> api.listActivityZonesAsync(id).get();
	}
}
