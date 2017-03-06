package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivityZone;
import test.api.rest.activity.ListActivityZonesTest;
import test.api.rest.callback.TestListArrayCallback;

public class ListActivityZonesAsyncTest extends ListActivityZonesTest {
	/**
	 * @see test.api.rest.activity.ListActivityZonesTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaActivityZone, Long> listCallback() {
		return (api, id) -> api.listActivityZonesAsync(id).get();
	}
}
