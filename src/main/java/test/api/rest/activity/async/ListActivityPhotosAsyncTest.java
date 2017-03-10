package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.rest.API;
import test.api.rest.activity.ListActivityPhotosTest;
import test.api.rest.callback.APIListCallback;

/**
 * <p>
 * Specific tests for {@link API#listActivityPhotosAsync(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityPhotosAsyncTest extends ListActivityPhotosTest {
	/**
	 * @see test.api.rest.activity.ListActivityPhotosTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaPhoto, Long> listCallback() {
		return (api, id) -> api.listActivityPhotosAsync(id).get();
	}
}
