package test.api.rest.activity.async;

import test.api.rest.activity.ListActivityKudoersTest;
import test.utils.TestUtils;

public class ListActivityKudoersAsyncTest extends ListActivityKudoersTest {
	/**
	 *
	 */
	public ListActivityKudoersAsyncTest() {
		this.listCallback = (api, id) -> api.listActivityKudoersAsync(id, null, null).get();
		this.pagingCallback = paging -> api()
				.listActivityKudoersAsync(TestUtils.ACTIVITY_WITH_KUDOS, paging.getPage(), paging.getPageSize()).get();
	}

}
