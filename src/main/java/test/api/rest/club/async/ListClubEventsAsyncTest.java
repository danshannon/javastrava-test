package test.api.rest.club.async;

import test.api.rest.club.ListClubEventsTest;

/**
 * @author Dan Shannon
 *
 */
public class ListClubEventsAsyncTest extends ListClubEventsTest {
	/**
	 *
	 */
	public ListClubEventsAsyncTest() {
		this.listCallback = (api, id) -> api.listClubGroupEventsAsync(id).get();
		this.suppressPagingTests = true;
	}

}
