/**
 *
 */
package test.api.rest.segment.async;

import test.api.rest.segment.ListStarredSegmentsTest;

/**
 * @author danshannon
 *
 */
public class ListStarredSegmentsAsyncTest extends ListStarredSegmentsTest {
	/**
	 * No-args constructor provides the callbacks
	 */
	public ListStarredSegmentsAsyncTest() {
		this.listCallback = (api, id) -> api.listStarredSegmentsAsync(id, null, null).get();
		this.pagingCallback = paging -> api().listStarredSegmentsAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}
}
