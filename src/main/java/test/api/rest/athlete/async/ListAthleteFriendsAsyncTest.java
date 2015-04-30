package test.api.rest.athlete.async;

import test.api.rest.athlete.ListAthleteFriendsTest;

public class ListAthleteFriendsAsyncTest extends ListAthleteFriendsTest {
	/**
	 *
	 */
	public ListAthleteFriendsAsyncTest() {
		this.listCallback = (api, id) -> api.listAthleteFriendsAsync(id, null, null).get();
		this.pagingCallback = (paging) -> api()
				.listAthleteFriendsAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}

}
