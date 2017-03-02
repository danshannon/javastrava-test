package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivityZone;
import test.api.rest.TestListArrayCallback;
import test.api.rest.activity.ListActivityZonesTest;

public class ListActivityZonesAsyncTest extends ListActivityZonesTest {
	/**
	 * @see test.api.rest.activity.ListActivityZonesTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaActivityZone, Long> listCallback() {
		return (api, id) -> api.listActivityZonesAsync(id).get();
	}
}
