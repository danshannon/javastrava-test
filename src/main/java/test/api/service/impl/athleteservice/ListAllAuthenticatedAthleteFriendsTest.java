package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;

public class ListAllAuthenticatedAthleteFriendsTest extends StravaTest {
	@Test
	public void testListAllAuthenticatedAthleteFriends() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> athletes = strava().listAllAuthenticatedAthleteFriends();
				assertNotNull(athletes);
				assertEquals(strava().getAuthenticatedAthlete().getFriendCount().intValue(), athletes.size());
				for (final StravaAthlete athlete : athletes) {
					StravaAthleteTest.validateAthlete(athlete);
				}
			}
		});
	}

}
