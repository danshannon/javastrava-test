package test.service.impl.routeservice;

import javastrava.api.v3.model.StravaRoute;
import javastrava.api.v3.service.Strava;
import test.api.model.StravaRouteTest;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.RouteDataUtils;

/**
 * <p>
 * Tests for {@link Strava#getRoute(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetRouteTest extends GetMethodTest<StravaRoute, Integer> {

	@Override
	protected Integer getIdInvalid() {
		return RouteDataUtils.ROUTE_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return null;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer getIdValid() {
		return RouteDataUtils.ROUTE_VALID_ID;
	}

	@Override
	protected GetCallback<StravaRoute, Integer> getter() throws Exception {
		return ((strava, id) -> strava.getRoute(id));
	}

	@Override
	protected void validate(StravaRoute object) {
		StravaRouteTest.validate(object);

	}

}
