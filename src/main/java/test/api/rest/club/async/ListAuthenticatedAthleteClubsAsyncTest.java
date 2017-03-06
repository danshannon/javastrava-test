package test.api.rest.club.async;

import javastrava.api.v3.model.StravaClub;
import test.api.rest.callback.TestListArrayCallback;
import test.api.rest.club.ListAuthenticatedAthleteClubsTest;

public class ListAuthenticatedAthleteClubsAsyncTest extends ListAuthenticatedAthleteClubsTest {
	/**
	 * @see test.api.rest.club.ListAuthenticatedAthleteClubsTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaClub, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteClubsAsync().get();
	}
}
