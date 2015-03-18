package test.api.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.ClubService;
import javastrava.api.v3.service.impl.ClubServiceImpl;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.utils.TestUtils;


public class ListAllClubMembersTest {
	@Test
	public void testListAllClubMembers_validClub() {
		List<StravaAthlete> athletes = service().listAllClubMembers(TestUtils.CLUB_VALID_ID);
		assertNotNull(athletes);
		for (StravaAthlete athlete : athletes) {
			StravaAthleteTest.validateAthlete(athlete);
		}
	}
	
	@Test
	public void testListAllClubMembers_invalidClub() {
		List<StravaAthlete> athletes = service().listAllClubMembers(TestUtils.CLUB_INVALID_ID);
		assertNull(athletes);
	}
	
	@Test
	public void testListAllClubMembers_privateMember() {
		List<StravaAthlete> athletes = service().listAllClubMembers(TestUtils.CLUB_PRIVATE_MEMBER_ID);
		assertNotNull(athletes);
		for (StravaAthlete athlete : athletes) {
			StravaAthleteTest.validateAthlete(athlete);
		}
	}

	@Test
	public void testListAllClubMembers_privateNonMember() {
		List<StravaAthlete> athletes = service().listAllClubMembers(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
		assertNotNull(athletes);
		assertEquals(0,athletes.size());
		
	}
	
	@Test
	public void testListAllClubMembers_publicNonMember() {
		List<StravaAthlete> athletes = service().listAllClubMembers(TestUtils.CLUB_PUBLIC_NON_MEMBER_ID);
		assertNotNull(athletes);
		for (StravaAthlete athlete : athletes) {
			StravaAthleteTest.validateAthlete(athlete);
		}
		
	}

	private ClubService service() {
		return ClubServiceImpl.instance(TestUtils.getValidToken());
	}
}
