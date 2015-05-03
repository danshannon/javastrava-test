package test.api.rest.activity.async;

import test.api.rest.activity.ListActivityKudoersTest;
import test.issues.strava.Issue95;
import test.utils.TestUtils;

public class ListActivityKudoersAsyncTest extends ListActivityKudoersTest {
	/**
	 *
	 */
	public ListActivityKudoersAsyncTest() {
		this.listCallback = (api, id) -> api.listActivityKudoersAsync(id, null, null).get();
		this.pagingCallback = paging -> api().listActivityKudoersAsync(TestUtils.ACTIVITY_WITH_KUDOS, paging.getPage(), paging.getPageSize()).get();
	}

	/**
	 * @see test.api.rest.activity.ListActivityKudoersTest#list_privateWithoutViewPrivate()
	 */
	@Override
	public void list_privateWithoutViewPrivate() throws Exception {
		// TODO This is a workaround for Javastrava issue #95
		if (new Issue95().isIssue()) {
			return;
		}

		super.list_privateWithoutViewPrivate();
	}

}
