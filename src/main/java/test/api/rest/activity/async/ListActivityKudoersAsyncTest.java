package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaAthlete;
import test.api.rest.activity.ListActivityKudoersTest;
import test.api.rest.callback.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.ActivityDataUtils;

public class ListActivityKudoersAsyncTest extends ListActivityKudoersTest {
	/**
	 * @see test.api.rest.activity.ListActivityKudoersTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listActivityKudoersAsync(ActivityDataUtils.ACTIVITY_WITH_KUDOS, paging.getPage(), paging.getPageSize())
				.get();
	}

	/**
	 * @see test.api.rest.activity.ListActivityKudoersTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaAthlete, Long> listCallback() {
		return (api, id) -> api.listActivityKudoersAsync(id, null, null).get();
	}
}
