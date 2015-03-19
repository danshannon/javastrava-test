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
import test.utils.TestUtils;

public class ListAllAthleteKOMsTest extends StravaTest {
	@Test
	public void testListAllAthleteKOMs_authenticatedAthlete() {
		final List<StravaSegmentEffort> efforts = service().listAllAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
		assertNotNull(efforts);
		for (final StravaSegmentEffort effort : efforts) {
			StravaSegmentEffortTest.validateSegmentEffort(effort);
			assertTrue("Segment " + effort.getSegment().getId() + " athlete " + TestUtils.ATHLETE_AUTHENTICATED_ID + " is not the KOM!",isKom(effort.getSegment(),TestUtils.ATHLETE_AUTHENTICATED_ID));
		}
	}

	@Test
	public void testListAllAthleteKOMs_otherAthlete() {
		final List<StravaSegmentEffort> efforts = service().listAllAthleteKOMs(TestUtils.ATHLETE_VALID_ID);
		assertNotNull(efforts);
//		TODO workaround for issue javastrava-api #32 - see https://github.com/danshannon/javastravav3api/issues/32
//		for (final StravaSegmentEffort effort : efforts) {
//			StravaSegmentEffortTest.validateSegmentEffort(effort);
//			assertTrue("Segment " + effort.getSegment().getId() + " athlete " + TestUtils.ATHLETE_VALID_ID + " is not the KOM!",isKom(effort.getSegment(),TestUtils.ATHLETE_VALID_ID));
//		}
	}

	@Test
	public void testListAllAthleteKOMs_invalidAthlete() {
		final List<StravaSegmentEffort> efforts = service().listAllAthleteKOMs(TestUtils.ATHLETE_INVALID_ID);
		assertNull(efforts);
	}

	private boolean isKom(final StravaSegment segment, final Integer athleteId) {
		final StravaSegmentLeaderboard leaderboard = service().getSegmentLeaderboard(segment.getId());
		boolean isKom = false;
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			if (entry.getAthleteId().equals(athleteId) && entry.getRank().equals(1)) {
				isKom = true;
			}
		}
		return isKom;
	}

}
