package test.api.rest.athlete.async;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import test.api.rest.athlete.ListAthleteFriendsTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;

/**
 * <p>
 * Specific tests for {@link API#listAthleteFriendsAsync(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAthleteFriendsAsyncTest extends ListAthleteFriendsTest {
	/**
	 * @see test.api.rest.athlete.ListAthleteFriendsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listAthleteFriendsAsync(id, null, null).get();
	}

	/**
	 * @see test.api.rest.athlete.ListAthleteFriendsTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listAthleteFriendsAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}
}
