package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAthletesBothFollowingTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Override
	protected ListCallback<StravaAthlete> callback() {
		return (paging -> strava().listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, paging));
	}

	// 2. Invalid other athlete
	@Test
	public void testListAthletesBothFollowing_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> friends = strava().listAthletesBothFollowing(TestUtils.ATHLETE_INVALID_ID);
			assertNull("Returned list of athletes being followed by invalid athlete", friends);
		});
	}

	// 3. Private athlete
	@Test
	public void testListAthletesBothFollowing_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> friends = strava().listAthletesBothFollowing(TestUtils.ATHLETE_PRIVATE_ID);
			assertNotNull(friends);
			assertTrue(friends.isEmpty());
		});
	}

	// Test cases
	// 1. Valid athlete - at least 1 common friend
	@Test
	public void testListAthletesBothFollowing_validAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> friends = strava().listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID);
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
