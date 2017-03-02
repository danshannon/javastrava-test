package test.api.rest.athlete.async;

import javastrava.api.v3.model.StravaAthlete;
import test.api.rest.TestListArrayCallback;
import test.api.rest.athlete.ListAthletesBothFollowingTest;
import test.api.rest.util.ArrayCallback;

public class ListAthletesBothFollowingAsyncTest extends ListAthletesBothFollowingTest {
	/**
	 * @see test.api.rest.athlete.ListAthletesBothFollowingTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listAthletesBothFollowingAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}

	/**
	 * @see test.api.rest.athlete.ListAthletesBothFollowingTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listAthletesBothFollowingAsync(id, null, null).get();
	}

}
