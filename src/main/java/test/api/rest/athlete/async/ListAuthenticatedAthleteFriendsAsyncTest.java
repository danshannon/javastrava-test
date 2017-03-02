package test.api.rest.athlete.async;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaAthleteTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.athlete.ListAuthenticatedAthleteFriendsTest;
import test.api.rest.util.ArrayCallback;
import test.utils.RateLimitedTestRunner;

public class ListAuthenticatedAthleteFriendsAsyncTest extends ListAuthenticatedAthleteFriendsTest {
	/**
	 * @see test.api.rest.athlete.ListAuthenticatedAthleteFriendsTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listAuthenticatedAthleteFriendsAsync(paging.getPage(), paging.getPageSize()).get();
	}

	/**
	 * @see test.api.rest.athlete.ListAuthenticatedAthleteFriendsTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteFriendsAsync(null, null).get();
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
		});
	}

}
