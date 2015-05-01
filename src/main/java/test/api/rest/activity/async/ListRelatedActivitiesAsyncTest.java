package test.api.rest.activity.async;

import test.api.rest.activity.ListRelatedActivitiesTest;

public class ListRelatedActivitiesAsyncTest extends ListRelatedActivitiesTest {
	/**
<<<<<<< HEAD
	 *
	 */
	public ListRelatedActivitiesAsyncTest() {
		this.listCallback = (api, id) -> api.listRelatedActivitiesAsync(id, null, null).get();
		this.pagingCallback = paging -> api()
				.listRelatedActivitiesAsync(validId(), paging.getPage(), paging.getPageSize()).get();
=======
	 * No-arguments constructor provides the required callbacks
	 */
	public ListRelatedActivitiesAsyncTest() {
		this.listCallback = (api, id) -> api.listRelatedActivitiesAsync(id, null, null).get();
		this.pagingCallback = paging -> api().listRelatedActivitiesAsync(validId(), paging.getPage(), paging.getPageSize()).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

}
