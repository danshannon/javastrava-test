package test.api.rest.athlete.async;

import javastrava.api.v3.model.StravaAthlete;
import test.api.rest.athlete.ListAthleteFriendsTest;
import test.api.rest.callback.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;

public class ListAthleteFriendsAsyncTest extends ListAthleteFriendsTest {
	/**
	 * @see test.api.rest.athlete.ListAthleteFriendsTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listAthleteFriendsAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}

	/**
	 * @see test.api.rest.athlete.ListAthleteFriendsTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listAthleteFriendsAsync(id, null, null).get();
	}
}
