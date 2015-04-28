package test.api.rest.athlete;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.issues.strava.Issue83;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAthletesBothFollowingTest extends PagingArrayMethodTest<StravaAthlete, Integer> {
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return (paging -> api().listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, paging.getPage(), paging.getPageSize()));
	}

	// 2. Invalid other athlete
	@Test
	public void testListAthletesBothFollowing_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listAthletesBothFollowing(TestUtils.ATHLETE_INVALID_ID, null, null);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Retuend list of friends for a non-existent athlete!");
		});
	}

	// 3. Private athlete
	@Test
	public void testListAthletesBothFollowing_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#83
				if (new Issue83().isIssue()) {
					return;
				}
				// End of workaround

				try {
					api().listAthletesBothFollowing(TestUtils.ATHLETE_PRIVATE_ID, null, null);
				} catch (final UnauthorizedException e) {
					// Expected
					return;
				}
				fail("Returned list of friends for a private athlete!");
			});
	}

	// Test cases
	// 1. Valid athlete - at least 1 common friend
	@Test
	public void testListAthletesBothFollowing_validAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] friends = api().listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, null, null);
			assertNotNull(friends);
			assertFalse(friends.length == 0);
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
