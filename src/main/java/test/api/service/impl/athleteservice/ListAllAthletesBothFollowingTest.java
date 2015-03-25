package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class ListAllAthletesBothFollowingTest extends StravaTest {
	@Test
	public void testListAllAthletesBothFollowing_validAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> athletes = strava().listAllAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID);
				assertNotNull(athletes);
				for (final StravaAthlete athlete : athletes) {
					StravaAthleteTest.validateAthlete(athlete);
				}
			}
		});
	}

	@Test
	public void testListAllAthletesBothFollowing_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> athletes = strava().listAllAthletesBothFollowing(TestUtils.ATHLETE_INVALID_ID);
				assertNull(athletes);
			}
		});
	}

	@Test
	public void testListAllAthletesBothFollowing_sameAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> athletes = strava().listAllAthletesBothFollowing(TestUtils.ATHLETE_AUTHENTICATED_ID);
				assertNotNull(athletes);

				// Will have returned all the athletes that the authenticated user is following
				final List<StravaAthlete> friends = strava().listAllAuthenticatedAthleteFriends();
				assertEquals(friends.size(), athletes.size());
			}
		});
	}

	@Test
	public void testListAllAthletesBothFollowing_athleteHasNoFriends() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> athletes = strava().listAllAthletesBothFollowing(TestUtils.ATHLETE_WITHOUT_FRIENDS);
				assertNotNull(athletes);
				assertEquals(0, athletes.size());
			}
		});
	}

}
