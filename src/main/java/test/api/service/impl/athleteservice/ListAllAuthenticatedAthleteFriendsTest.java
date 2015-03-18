package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.AthleteService;
import javastrava.api.v3.service.impl.AthleteServiceImpl;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.utils.TestUtils;

public class ListAllAuthenticatedAthleteFriendsTest {
	@Test
	public void testListAllAuthenticatedAthleteFriends() {
		List<StravaAthlete> athletes = service().listAllAuthenticatedAthleteFriends();
		assertNotNull(athletes);
		assertEquals(service().getAuthenticatedAthlete().getFriendCount().intValue(),athletes.size());
		for (StravaAthlete athlete : athletes) {
			StravaAthleteTest.validateAthlete(athlete);
		}
		
	}
	
	private AthleteService service() {
		return AthleteServiceImpl.instance(TestUtils.getValidToken());
	}


}
