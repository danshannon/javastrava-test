package test.api.rest.athlete.async;

import test.api.rest.athlete.ListAthletesBothFollowingTest;

public class ListAthletesBothFollowingAsyncTest extends ListAthletesBothFollowingTest {
	/**
	 *
	 */
	public ListAthletesBothFollowingAsyncTest() {
		this.listCallback = (api, id) -> api.listAthletesBothFollowingAsync(id, null, null).get();
		this.pagingCallback = (paging) -> api()
				.listAthletesBothFollowingAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}
}
