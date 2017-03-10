/**
 *
 */
package test.api.rest.segment.async;

import javastrava.api.v3.model.StravaSegment;
import test.api.rest.callback.APIListCallback;
import test.api.rest.segment.ListStarredSegmentsTest;
import test.api.rest.util.ArrayCallback;

/**
 * @author Dan Shannon
 *
 */
public class ListStarredSegmentsAsyncTest extends ListStarredSegmentsTest {
	/**
	 * @see test.api.rest.segment.ListStarredSegmentsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaSegment, Integer> listCallback() {
		return (api, id) -> api.listStarredSegmentsAsync(id, null, null).get();
	}

	/**
	 * @see test.api.rest.segment.ListStarredSegmentsTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaSegment> pagingCallback() {
		return paging -> api().listStarredSegmentsAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}
}
