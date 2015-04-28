package test.api.rest.activity.async;

import test.api.rest.activity.ListActivityLapsTest;

public class ListActivityLapsAsyncTest extends ListActivityLapsTest {
	/**
	 *
	 */
	public ListActivityLapsAsyncTest() {
		this.listCallback = (api, id) -> api.listActivityLapsAsync(id).get();
		this.pagingCallback = null;
		this.suppressPagingTests = true;
	}

}
