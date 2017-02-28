package test.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaFollowerState;
import test.api.model.StravaAthleteTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for list all athletes both following methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllAthletesBothFollowingTest extends ListMethodTest<StravaAthlete, Integer> {
	/**
	 * <p>
	 * Test it works even if you specify yourself
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAthletesBothFollowing_sameAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = TestUtils.strava()
					.listAllAthletesBothFollowing(TestUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(athletes);
			int friendCount = 0;

			// Will have returned all the athletes that the authenticated user is following
			final List<StravaAthlete> friends = TestUtils.strava().listAllAuthenticatedAthleteFriends();
			for (final StravaAthlete athlete : friends) {
				if (athlete.getFriend() == StravaFollowerState.ACCEPTED) {
					friendCount++;
				}
			}
			assertEquals(friendCount, athletes.size());
		});
	}

	@Override
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listAllAthletesBothFollowing(id));
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return TestUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected Integer idValidWithEntries() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return TestUtils.ATHLETE_WITHOUT_FRIENDS;
	}

	@Override
	protected Integer idInvalid() {
		return TestUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected void validate(StravaAthlete object) {
		StravaAthleteTest.validateAthlete(object);
	}

}
