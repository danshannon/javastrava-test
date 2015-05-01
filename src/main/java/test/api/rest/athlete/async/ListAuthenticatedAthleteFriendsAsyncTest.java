package test.api.rest.athlete.async;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaAthleteTest;
import test.api.rest.athlete.ListAuthenticatedAthleteFriendsTest;
import test.utils.RateLimitedTestRunner;

public class ListAuthenticatedAthleteFriendsAsyncTest extends ListAuthenticatedAthleteFriendsTest {
	/**
	 *
	 */
	public ListAuthenticatedAthleteFriendsAsyncTest() {
		this.listCallback = (api, id) -> api.listAuthenticatedAthleteFriendsAsync(null, null).get();
		this.pagingCallback = (paging) -> api()
				.listAuthenticatedAthleteFriendsAsync(paging.getPage(), paging.getPageSize()).get();
	}

	@Override
	public void testListAuthenticatedAthleteFriends_friends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] friends = api().listAuthenticatedAthleteFriendsAsync(null, null).get();
			assertNotNull(friends);
			assertFalse(friends.length == 0);
			for (final StravaAthlete athlete : friends) {
				StravaAthleteTest.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
			}
		} );
	}

}
