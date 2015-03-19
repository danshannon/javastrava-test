package test.api.service.impl.clubservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaClub;

import org.junit.Test;

import test.api.model.StravaClubTest;
import test.api.service.StravaTest;

public class ListAuthenticatedAthleteClubsTest extends StravaTest {
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

}
