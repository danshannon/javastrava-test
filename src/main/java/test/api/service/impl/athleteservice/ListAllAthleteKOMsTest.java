package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAllAthleteKOMsTest extends StravaTest {
	private boolean isKom(final StravaSegment segment, final Integer athleteId) {
		final StravaSegmentLeaderboard leaderboard = strava().getSegmentLeaderboard(segment.getId());
		boolean isKom = false;
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			if (entry.getAthleteId().equals(athleteId) && entry.getRank().equals(1)) {
				isKom = true;
			}
		}
		return isKom;
	}

	@Test
	public void testListAllAthleteKOMs_authenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAllAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				StravaSegmentEffortTest.validateSegmentEffort(effort);
				assertTrue("Segment " + effort.getSegment().getId() + " athlete " + TestUtils.ATHLETE_AUTHENTICATED_ID
						+ " is not the KOM!", isKom(effort.getSegment(), TestUtils.ATHLETE_AUTHENTICATED_ID));
			}
		});
	}

	@Test
	public void testListAllAthleteKOMs_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAllAthleteKOMs(TestUtils.ATHLETE_INVALID_ID);
			assertNull(efforts);
		});
	}

	@Test
	public void testListAllAthleteKOMs_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAllAthleteKOMs(TestUtils.ATHLETE_VALID_ID);
			assertNotNull(efforts);
			// TODO workaround for issue javastrava-api #32 - see https://github.com/danshannon/javastravav3api/issues/32
			// for (final StravaSegmentEffort effort : efforts) {
			// StravaSegmentEffortTest.validateSegmentEffort(effort);
			// assertTrue("Segment " + effort.getSegment().getId() + " athlete " + TestUtils.ATHLETE_VALID_ID +
			// " is not the KOM!",isKom(effort.getSegment(),TestUtils.ATHLETE_VALID_ID));
			// }
			});
	}

}
