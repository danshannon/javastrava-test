package test.api.club.async;

import javastrava.model.StravaClubEvent;
import test.api.callback.APIListCallback;
import test.api.club.ListClubEventsTest;

/**
 * @author Dan Shannon
 *
 */
public class ListClubEventsAsyncTest extends ListClubEventsTest {
	/**
	 * @see test.api.club.ListClubEventsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaClubEvent, Integer> listCallback() {
		return (api, id) -> api.listClubGroupEventsAsync(id).get();
	}
}
