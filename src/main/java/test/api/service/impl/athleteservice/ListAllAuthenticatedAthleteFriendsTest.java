package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaFollowerState;
import test.api.model.StravaAthleteTest;
import test.api.service.standardtests.ListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific test for list all authenticated athlete friends
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllAuthenticatedAthleteFriendsTest extends ListMethodTest<StravaAthlete, Integer> {
	/**
	 * <p>
	 * Check that the number of friends is indeed the number of friends
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Test
	public void testListAllAuthenticatedAthleteFriends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = lister().getList(TestUtils.strava(), TestUtils.ATHLETE_AUTHENTICATED_ID);
			int friendCount = 0;
			for (final StravaAthlete athlete : athletes) {
				if (athlete.getFriend() == StravaFollowerState.ACCEPTED) {
					friendCount++;
				}
			}
			assertNotNull(athletes);
			assertEquals(TestUtils.strava().getAuthenticatedAthlete().getFriendCount().intValue(), friendCount);
			for (final StravaAthlete athlete : athletes) {
				StravaAthleteTest.validateAthlete(athlete);
			}
		});
	}

	@Override
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listAllAuthenticatedAthleteFriends());
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected Integer idInvalid() {
		return null;
	}

	@Override
	protected void validate(StravaAthlete object) {
		StravaAthleteTest.validateAthlete(object);
	}

}
