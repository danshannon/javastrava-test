package test.api.rest.activity.async;

import test.api.rest.activity.ListActivityPhotosTest;

public class ListActivityPhotosAsyncTest extends ListActivityPhotosTest {
	/**
	 *
	 */
	public ListActivityPhotosAsyncTest() {
		super();
		this.listCallback = (api, id) -> api.listActivityPhotosAsync(id).get();
		this.pagingCallback = null;
		this.suppressPagingTests = true;
		this.listOtherReturns401Unauthorised = true;
	}

}
