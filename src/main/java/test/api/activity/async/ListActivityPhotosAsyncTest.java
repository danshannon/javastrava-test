package test.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaPhoto;
import test.api.activity.ListActivityPhotosTest;
import test.api.callback.APIListCallback;

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
	 * @see test.api.activity.ListActivityPhotosTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaPhoto, Long> listCallback() {
		return (api, id) -> api.listActivityPhotosAsync(id).get();
	}
}
