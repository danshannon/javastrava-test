package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.api.service.standardtests.ListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for list all athlete friends methods
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class ListAllAthleteFriendsTest extends ListMethodTest<StravaAthlete, Integer> {
	/**
	 * <p>
	 * Test that you can list another athlete's friends
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAthleteFriends_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = TestUtils.strava().listAllAthleteFriends(TestUtils.ATHLETE_VALID_ID);
			assertNotNull(athletes);
			for (final StravaAthlete athlete : athletes) {
				StravaAthleteTest.validateAthlete(athlete);
			}
		});
	}

	@Override
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listAllAthleteFriends(id));
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
