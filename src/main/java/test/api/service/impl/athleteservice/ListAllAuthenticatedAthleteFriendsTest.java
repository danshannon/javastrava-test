package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.StravaTest;

public class ListAllAuthenticatedAthleteFriendsTest extends StravaTest {
	@Test
	public void testListAllAuthenticatedAthleteFriends() {
		List<StravaAthlete> athletes = service().listAllAuthenticatedAthleteFriends();
		assertNotNull(athletes);
		assertEquals(service().getAuthenticatedAthlete().getFriendCount().intValue(),athletes.size());
		for (StravaAthlete athlete : athletes) {
			StravaAthleteTest.validateAthlete(athlete);
		}
		
	}
	
}
