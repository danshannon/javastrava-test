package test.api.rest.route;

import javastrava.api.v3.model.StravaRoute;
import javastrava.api.v3.rest.API;
import test.api.model.StravaRouteTest;
import test.api.rest.APIGetTest;
import test.api.rest.TestGetCallback;
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
	protected TestGetCallback<StravaRoute, Integer> getCallback() {
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
	protected Integer validId() {
		return RouteDataUtils.ROUTE_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(StravaRoute result) throws Exception {
		StravaRouteTest.validate(result);

	}

}
