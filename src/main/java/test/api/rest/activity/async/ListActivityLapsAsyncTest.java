package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaLap;
import test.api.rest.activity.ListActivityLapsTest;
import test.api.rest.callback.TestListArrayCallback;

public class ListActivityLapsAsyncTest extends ListActivityLapsTest {
	/**
	 * @see test.api.rest.activity.ListActivityLapsTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaLap, Long> listCallback() {
		return (api, id) -> api.listActivityLapsAsync(id).get();
	}
}
