package test.api.rest.activity.async;

import test.api.rest.activity.ListRelatedActivitiesTest;

public class ListRelatedActivitiesAsyncTest extends ListRelatedActivitiesTest {
	/**
	 *
	 */
	public ListRelatedActivitiesAsyncTest() {
		this.listCallback = (api, id) -> api.listRelatedActivitiesAsync(id, null, null).get();
		this.pagingCallback = paging -> api()
				.listRelatedActivitiesAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}

}
