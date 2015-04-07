package test.api.rest.club;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaClub;

import org.junit.Test;

import test.api.model.StravaClubTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;

public class ListAuthenticatedAthleteClubsTest extends APITest {
	// Test cases
	// 1. StravaAthlete has clubs
	@Test
	public void testListAuthenticatedAthleteClubs() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub[] clubs = api().listAuthenticatedAthleteClubs();
			assertNotNull(clubs);
			assertFalse(clubs.length == 0);
			for (final StravaClub club : clubs) {
				StravaClubTest.validate(club);
			}
		});
	}

}
