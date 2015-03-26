package test.api.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAllClubMembersTest extends StravaTest {
	@Test
	public void testListAllClubMembers_invalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = strava().listAllClubMembers(TestUtils.CLUB_INVALID_ID);
			assertNull(athletes);
		});
	}

	@Test
	public void testListAllClubMembers_privateMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = strava().listAllClubMembers(TestUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(athletes);
			for (final StravaAthlete athlete : athletes) {
				StravaAthleteTest.validateAthlete(athlete);
			}
		});
	}

	@Test
	public void testListAllClubMembers_privateNonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = strava().listAllClubMembers(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			assertNotNull(athletes);
			assertEquals(0, athletes.size());
		});
	}

	@Test
	public void testListAllClubMembers_publicNonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = strava().listAllClubMembers(TestUtils.CLUB_PUBLIC_NON_MEMBER_ID);
			assertNotNull(athletes);
			for (final StravaAthlete athlete : athletes) {
				StravaAthleteTest.validateAthlete(athlete);
			}
		});
	}

	@Test
	public void testListAllClubMembers_validClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = strava().listAllClubMembers(TestUtils.CLUB_VALID_ID);
			assertNotNull(athletes);
			for (final StravaAthlete athlete : athletes) {
				StravaAthleteTest.validateAthlete(athlete);
			}
		});
	}

}
