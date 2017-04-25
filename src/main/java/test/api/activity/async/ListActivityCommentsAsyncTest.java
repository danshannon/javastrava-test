package test.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaComment;
import test.api.activity.ListActivityCommentsTest;
import test.api.callback.APIListCallback;
import test.api.util.ArrayCallback;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Specific tests for {@link API#listActivityCommentsAsync(Long, Boolean, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityCommentsAsyncTest extends ListActivityCommentsTest {

	/**
	 * @see test.api.activity.ListActivityCommentsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaComment, Long> listCallback() {
		return (api, id) -> api.listActivityCommentsAsync(id, null, null, null).get();
	}

	/**
	 * @see test.api.activity.ListActivityCommentsTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaComment> pagingCallback() {
		return paging -> api().listActivityCommentsAsync(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, null, paging.getPage(), paging.getPageSize()).get();
	}
}
