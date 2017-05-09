package test.api.route;

import javastrava.api.API;
import javastrava.model.StravaRoute;
import test.api.APIPagingListTest;
import test.api.callback.APIListCallback;
import test.api.util.ArrayCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.RouteDataUtils;

/**
 * <p>
 * Tests for {@link API#listAthleteRoutes(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListRoutesTest extends APIPagingListTest<StravaRoute, Integer> {

	@Override
	protected Integer invalidId() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected APIListCallback<StravaRoute, Integer> listCallback() {
		return ((api, id) -> api.listAthleteRoutes(id, null, null));
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

	@Override
	protected ArrayCallback<StravaRoute> pagingCallback() {
		return paging -> api().listAthleteRoutes(validId(), paging.getPage(), paging.getPageSize());
	}

}
