package test.api.rest.route.async;

import javastrava.api.v3.model.StravaRoute;
import javastrava.api.v3.rest.API;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.route.GetRouteTest;

/**
 * <p>
 * Test for {@link API#getRouteAsync(Integer)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetRouteAsyncTest extends GetRouteTest {

	@Override
	protected APIGetCallback<StravaRoute, Integer> getter() {
		return ((api, id) -> api.getRouteAsync(id).get());
	}

}
