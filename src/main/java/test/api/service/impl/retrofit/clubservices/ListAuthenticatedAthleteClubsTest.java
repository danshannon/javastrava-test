package test.api.service.impl.retrofit.clubservices;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.service.ClubServices;
import javastrava.api.v3.service.impl.retrofit.ClubServicesImpl;

import org.junit.Test;

import test.api.model.StravaClubTest;
import test.utils.TestUtils;

public class ListAuthenticatedAthleteClubsTest {
	// Test cases
	// 1. StravaAthlete has clubs
	@Test
	public void testListAuthenticatedAthleteClubs() {
		final List<StravaClub> clubs = service().listAuthenticatedAthleteClubs();
		assertNotNull(clubs);
		assertFalse(clubs.size() == 0);
		for (final StravaClub club : clubs) {
			StravaClubTest.validate(club);
		}
	}

	private ClubServices service() {
		return ClubServicesImpl.implementation(TestUtils.getValidToken());
	}

}
