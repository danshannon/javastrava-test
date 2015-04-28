package test.api.rest.activity.async;

import test.api.rest.activity.ListActivityCommentsTest;
import test.utils.TestUtils;

public class ListActivityCommentsAsyncTest extends ListActivityCommentsTest {
	/**
	 *
	 */
	public ListActivityCommentsAsyncTest() {
		this.listCallback = (api, id) -> api.listActivityCommentsAsync(id, null, null, null).get();
		this.pagingCallback = paging -> api().listActivityCommentsAsync(TestUtils.ACTIVITY_WITH_COMMENTS, null,
				paging.getPage(), paging.getPageSize()).get();
	}

}
