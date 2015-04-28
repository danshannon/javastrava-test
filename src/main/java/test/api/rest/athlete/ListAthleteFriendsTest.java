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

public class ListAthleteFriendsTest extends PagingArrayMethodTest<StravaAthlete, Integer> {
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return (paging -> api().listAthleteFriends(TestUtils.ATHLETE_VALID_ID, paging.getPage(), paging.getPageSize()));
	}

	// Test cases
	// 2. Invalid athlete
	@Test
	public void testListAthleteFriends_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listAthleteFriends(TestUtils.ATHLETE_INVALID_ID, null, null);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}

			fail("Listed friends despite athlete id being invalid");
		});
	}

	@Test
	public void testListAthleteFriends_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#83
			Issue83 issue83 = new Issue83();
			if (issue83.isIssue()) {
				return;
			}
			// End of workaround
			
			try {
				api().listAthleteFriends(TestUtils.ATHLETE_PRIVATE_ID, null, null);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Listed friends despite athlete being flagged as private");
		});
	}

	@Test
	public void testListAthleteFriends_validAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] friends = api().listAthleteFriends(TestUtils.ATHLETE_VALID_ID, null, null);
			assertNotNull(friends);
			assertFalse(friends.length == 0);
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
