package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class ListAthletesBothFollowingTest extends PagingListMethodTest<StravaAthlete, Integer> {
	// Test cases
	// 1. Valid athlete - at least 1 common friend
	@Test
	public void testListAthletesBothFollowing_validAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> friends = strava().listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID);
				assertNotNull(friends);
				assertFalse(friends.size() == 0);
				for (final StravaAthlete athlete : friends) {
					StravaAthleteTest.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
				}
			}
		});
	}

	// 2. Invalid other athlete
	@Test
	public void testListAthletesBothFollowing_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> friends = strava().listAthletesBothFollowing(TestUtils.ATHLETE_INVALID_ID);
				assertNull("Returned list of athletes being followed by invalid athlete", friends);
			}
		});
	}

	// 3. Private athlete
	@Test
	public void testListAthletesBothFollowing_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> friends = strava().listAthletesBothFollowing(TestUtils.ATHLETE_PRIVATE_ID);
				assertNotNull(friends);
				assertTrue(friends.isEmpty());
			}
		});
	}

	@Override
	protected void validate(final StravaAthlete athlete, final Integer id, final StravaResourceState state) {
		StravaAthleteTest.validateAthlete(athlete, id, state);

	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		validate(athlete, athlete.getId(), athlete.getResourceState());
	}

	@Override
	protected ListCallback<StravaAthlete> callback() {
		return (new ListCallback<StravaAthlete>() {

			@Override
			public List<StravaAthlete> getList(final Paging paging) {
				return strava().listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, paging);
			}

		});
	}

}
