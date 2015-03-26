package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;

public class ListAuthenticatedAthleteFriendsTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Override
	protected ListCallback<StravaAthlete> callback() {
		return (paging -> strava().listAuthenticatedAthleteFriends(paging));
	}

	@Test
	public void testListAuthenticatedAthleteFriends_friends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> friends = strava().listAuthenticatedAthleteFriends();
			assertNotNull(friends);
			assertFalse(friends.isEmpty());
			for (final StravaAthlete athlete : friends) {
				StravaAthleteTest.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
			}
		});
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		validate(athlete, athlete.getId(), athlete.getResourceState());

	}

	@Override
	protected void validate(final StravaAthlete athlete, final Integer id, final StravaResourceState state) {
		StravaAthleteTest.validateAthlete(athlete, id, state);

	}

}
