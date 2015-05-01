package test.api.rest.club.async;

import test.api.rest.club.ListAuthenticatedAthleteClubsTest;

public class ListAuthenticatedAthleteClubsAsyncTest extends ListAuthenticatedAthleteClubsTest {
	/**
	 *
	 */
	public ListAuthenticatedAthleteClubsAsyncTest() {
		super();
		this.listCallback = (api, id) -> api.listAuthenticatedAthleteClubsAsync().get();
	}

}
