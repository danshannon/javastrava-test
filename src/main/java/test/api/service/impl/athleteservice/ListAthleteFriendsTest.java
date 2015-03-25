package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

public class ListAthleteFriendsTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Test
	public void testListAthleteFriends_validAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> friends = strava().listAthleteFriends(TestUtils.ATHLETE_VALID_ID);
				assertNotNull(friends);
				assertFalse(friends.size() == 0);
				for (final StravaAthlete athlete : friends) {
					StravaAthleteTest.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
				}
			}
		});
	}

	// Test cases
	// 2. Invalid athlete
	@Test
	public void testListAthleteFriends_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> friends = strava().listAthleteFriends(TestUtils.ATHLETE_INVALID_ID);

				assertNull("Listed friends despite athlete id being invalid", friends);
			}
		});
	}

	@Test
	public void testListAthleteFriends_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> friends = strava().listAthleteFriends(TestUtils.ATHLETE_PRIVATE_ID);
				assertNotNull(friends);
				for (final StravaAthlete athlete : friends) {
					StravaAthleteTest.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
				}
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
				return strava().listAthleteFriends(TestUtils.ATHLETE_VALID_ID, paging);
			}

		});
	}

}
