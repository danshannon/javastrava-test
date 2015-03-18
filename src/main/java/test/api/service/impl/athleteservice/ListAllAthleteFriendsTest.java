package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaFollowerState;
import javastrava.api.v3.service.AthleteService;
import javastrava.api.v3.service.impl.AthleteServiceImpl;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.utils.TestUtils;

public class ListAllAthleteFriendsTest {
	@Test
	public void testListAllAthleteFriends_authenticatedAthlete() {
		final List<StravaAthlete> athletes = service().listAllAthleteFriends(TestUtils.ATHLETE_AUTHENTICATED_ID);
		assertNotNull(athletes);
		for (final StravaAthlete athlete : athletes) {
			assertNotNull(athlete.getFriend());
			assertEquals(StravaFollowerState.ACCEPTED,athlete.getFriend());
			StravaAthleteTest.validateAthlete(athlete);
		}

	}

	@Test
	public void testListAllAthleteFriends_otherAthlete() {
		final List<StravaAthlete> athletes = service().listAllAthleteFriends(TestUtils.ATHLETE_VALID_ID);
		assertNotNull(athletes);
		for (final StravaAthlete athlete : athletes) {
			StravaAthleteTest.validateAthlete(athlete);
		}
	}

	@Test
	public void testListAllAthleteFriends_invalidAthlete() {
		final List<StravaAthlete> athletes = service().listAllAthleteFriends(TestUtils.ATHLETE_INVALID_ID);
		assertNull(athletes);
	}

	private AthleteService service() {
		return AthleteServiceImpl.instance(TestUtils.getValidToken());
	}


}
