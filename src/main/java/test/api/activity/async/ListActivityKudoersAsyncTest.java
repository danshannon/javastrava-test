package test.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaAthlete;
import test.api.activity.ListActivityKudoersTest;
import test.api.callback.APIListCallback;
import test.api.util.ArrayCallback;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Specific tests for {@link API#listActivityKudoersAsync(Long, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityKudoersAsyncTest extends ListActivityKudoersTest {
	/**
	 * @see test.api.activity.ListActivityKudoersTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaAthlete, Long> listCallback() {
		return (api, id) -> api.listActivityKudoersAsync(id, null, null).get();
	}

	/**
	 * @see test.api.activity.ListActivityKudoersTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listActivityKudoersAsync(ActivityDataUtils.ACTIVITY_WITH_KUDOS, paging.getPage(), paging.getPageSize()).get();
	}
}
