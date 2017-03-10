package test.service.impl.athleteservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.AthleteDataUtils;
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
	@Override
	protected Integer idInvalid() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return AthleteDataUtils.ATHLETE_WITHOUT_FRIENDS;
	}

	@Override
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listAllAthleteFriends(id));
	}

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
			final List<StravaAthlete> athletes = TestUtils.strava().listAllAthleteFriends(AthleteDataUtils.ATHLETE_VALID_ID);
			assertNotNull(athletes);
			for (final StravaAthlete athlete : athletes) {
				StravaAthleteTest.validateAthlete(athlete);
			}
		});
	}

	@Override
	protected void validate(StravaAthlete object) {
		StravaAthleteTest.validateAthlete(object);
	}

}
