package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaComment;
import test.api.rest.TestListArrayCallback;
import test.api.rest.activity.ListActivityCommentsTest;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.ActivityDataUtils;

public class ListActivityCommentsAsyncTest extends ListActivityCommentsTest {

	/**
	 * @see test.api.rest.activity.ListActivityCommentsTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaComment, Long> listCallback() {
		return (api, id) -> api.listActivityCommentsAsync(id, null, null, null).get();
	}

	/**
	 * @see test.api.rest.activity.ListActivityCommentsTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaComment> pagingCallback() {
		return paging -> api()
				.listActivityCommentsAsync(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, null, paging.getPage(), paging.getPageSize()).get();
	}
}
