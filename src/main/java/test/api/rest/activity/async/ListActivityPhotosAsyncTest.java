package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaPhoto;
import test.api.rest.activity.ListActivityPhotosTest;
import test.api.rest.callback.TestListArrayCallback;

public class ListActivityPhotosAsyncTest extends ListActivityPhotosTest {
	/**
	 * @see test.api.rest.activity.ListActivityPhotosTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaPhoto, Long> listCallback() {
		return (api, id) -> api.listActivityPhotosAsync(id).get();
	}
}
