package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.AthleteService;
import javastrava.api.v3.service.impl.AthleteServiceImpl;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.utils.TestUtils;

public class ListAllAthletesBothFollowingTest {
	@Test
	public void testListAllAthletesBothFollowing_validAthlete() {
		List<StravaAthlete> athletes = service().listAllAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID);
		assertNotNull(athletes);
		for (StravaAthlete athlete : athletes) {
			StravaAthleteTest.validateAthlete(athlete);
		}
	}
	
	@Test
	public void testListAllAthletesBothFollowing_invalidAthlete() {
		List<StravaAthlete> athletes = service().listAllAthletesBothFollowing(TestUtils.ATHLETE_INVALID_ID);
		assertNull(athletes);
		
	}
	
	@Test
	public void testListAllAthletesBothFollowing_sameAthlete() {
		List<StravaAthlete> athletes = service().listAllAthletesBothFollowing(TestUtils.ATHLETE_AUTHENTICATED_ID);
		assertNotNull(athletes);
		
		// Will have returned all the athletes that the authenticated user is following
		List<StravaAthlete> friends = service().listAllAuthenticatedAthleteFriends();
		assertEquals(friends.size(),athletes.size());
	}
	
	@Test
	public void testListAllAthletesBothFollowing_athleteHasNoFriends() {
		List<StravaAthlete> athletes = service().listAllAthletesBothFollowing(TestUtils.ATHLETE_WITHOUT_FRIENDS);
		assertNotNull(athletes);
		assertEquals(0,athletes.size());
		
	}

	private AthleteService service() {
		return AthleteServiceImpl.instance(TestUtils.getValidToken());
	}
}
