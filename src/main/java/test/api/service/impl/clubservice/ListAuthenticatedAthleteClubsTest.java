package test.api.service.impl.clubservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaClub;

import org.junit.Test;

import test.api.model.StravaClubTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;

public class ListAuthenticatedAthleteClubsTest extends StravaTest {
	// Test cases
	// 1. StravaAthlete has clubs
	@Test
	public void testListAuthenticatedAthleteClubs() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaClub> clubs = strava().listAuthenticatedAthleteClubs();
				assertNotNull(clubs);
				assertFalse(clubs.size() == 0);
				for (final StravaClub club : clubs) {
					StravaClubTest.validate(club);
				}
			}
		});
	}

}
