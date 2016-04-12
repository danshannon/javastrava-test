package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.standardtests.PagingListMethodTest;
import test.api.service.standardtests.callbacks.PagingListCallback;
import test.issues.strava.Issue83;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAthleteFriendsTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Override
	protected PagingListCallback<StravaAthlete> callback() {
		return (paging -> strava().listAthleteFriends(TestUtils.ATHLETE_VALID_ID, paging));
	}

	// Test cases
	// 2. Invalid athlete
	@Test
	public void testListAthleteFriends_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> friends = strava().listAthleteFriends(TestUtils.ATHLETE_INVALID_ID);

			assertNull("Listed friends despite athlete id being invalid", friends);
		});
	}

	@Test
	public void testListAthleteFriends_privateAthleteWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue83().isIssue()) {
				return;
			}
			final List<StravaAthlete> friends = strava().listAthleteFriends(TestUtils.ATHLETE_PRIVATE_ID);
			assertNotNull(friends);
			assertEquals(0, friends.size());
		});
	}

	@Test
	public void testListAthleteFriends_privateAthleteWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue83().isIssue()) {
				return;
			}
			final List<StravaAthlete> friends = strava().listAthleteFriends(TestUtils.ATHLETE_PRIVATE_ID);
			assertNotNull(friends);
			assertEquals(0, friends.size());
		});
	}
	
	@Test
	public void testListAthleteFriends_validAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> friends = strava().listAthleteFriends(TestUtils.ATHLETE_VALID_ID);
			assertNotNull(friends);
			assertFalse(friends.size() == 0);
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
