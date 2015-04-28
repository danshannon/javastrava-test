package test.api.rest.activity.async;

import test.api.rest.activity.ListActivityZonesTest;

public class ListActivityZonesAsyncTest extends ListActivityZonesTest {
	/**
	 *
	 */
	public ListActivityZonesAsyncTest() {
		super();
		this.listCallback = (api, id) -> api.listActivityZonesAsync(id).get();
		this.pagingCallback = null;
		this.suppressPagingTests = true;
	}

}
