package test.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaFollowerState;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.AthleteDataUtils;
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
	@Override
	protected Class<StravaAthlete> classUnderTest() {
		return StravaAthlete.class;
	}

	@Override
	protected Integer idInvalid() {
		return null;
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
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listAllAuthenticatedAthleteFriends());
	}

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
			final List<StravaAthlete> athletes = lister().getList(TestUtils.strava(), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
			int friendCount = 0;
			for (final StravaAthlete athlete : athletes) {
				if (athlete.getFriend() == StravaFollowerState.ACCEPTED) {
					friendCount++;
				}
			}
			assertNotNull(athletes);
			assertEquals(TestUtils.strava().getAuthenticatedAthlete().getFriendCount().intValue(), friendCount);
			for (final StravaAthlete athlete : athletes) {
				AthleteDataUtils.validateAthlete(athlete);
			}
		});
	}

	@Override
	protected void validate(StravaAthlete object) {
		AthleteDataUtils.validateAthlete(object);
	}

}
