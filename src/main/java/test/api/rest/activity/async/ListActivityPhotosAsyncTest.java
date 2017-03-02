package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaPhoto;
import test.api.rest.TestListArrayCallback;
import test.api.rest.activity.ListActivityPhotosTest;

public class ListActivityPhotosAsyncTest extends ListActivityPhotosTest {
	/**
	 * @see test.api.rest.activity.ListActivityPhotosTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaPhoto, Long> listCallback() {
		return (api, id) -> api.listActivityPhotosAsync(id).get();
	}
}
