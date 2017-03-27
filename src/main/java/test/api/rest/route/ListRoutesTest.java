package test.api.rest.route;

import javastrava.api.v3.model.StravaRoute;
import javastrava.api.v3.rest.API;
import test.api.rest.APIListTest;
import test.api.rest.callback.APIListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.RouteDataUtils;

/**
 * <p>
 * Tests for {@link API#listAthleteRoutes(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListRoutesTest extends APIListTest<StravaRoute, Integer> {

	@Override
	protected Integer invalidId() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected APIListCallback<StravaRoute, Integer> listCallback() {
		return ((api, id) -> api.listAthleteRoutes(id));
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
	protected void validateArray(StravaRoute[] list) throws Exception {
		for (final StravaRoute route : list) {
			RouteDataUtils.validateRoute(route);
		}
	}

	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
