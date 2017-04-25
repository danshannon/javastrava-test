package test.api.route;

import javastrava.api.API;
import javastrava.model.StravaRoute;
import test.api.APIGetTest;
import test.api.callback.APIGetCallback;
import test.service.standardtests.data.RouteDataUtils;

/**
 * <p>
 * Tests for {@link API#getRoute(Integer)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetRouteTest extends APIGetTest<StravaRoute, Integer> {

	@Override
	protected APIGetCallback<StravaRoute, Integer> getter() {
		return ((api, id) -> api.getRoute(id));
	}

	@Override
	protected Integer invalidId() {
		return RouteDataUtils.ROUTE_INVALID_ID;
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(StravaRoute result) throws Exception {
		RouteDataUtils.validateRoute(result);
	}

	@Override
	protected Integer validId() {
		return RouteDataUtils.ROUTE_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
