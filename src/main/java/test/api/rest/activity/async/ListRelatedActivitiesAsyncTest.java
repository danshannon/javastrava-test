package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaActivity;
import test.api.rest.activity.ListRelatedActivitiesTest;
import test.api.rest.callback.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;

public class ListRelatedActivitiesAsyncTest extends ListRelatedActivitiesTest {
	/**
	 * @see test.api.rest.activity.ListRelatedActivitiesTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return paging -> api().listRelatedActivitiesAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}

	/**
	 * @see test.api.rest.activity.ListRelatedActivitiesTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaActivity, Long> listCallback() {
		return (api, id) -> api.listRelatedActivitiesAsync(id, null, null).get();
	}
}
