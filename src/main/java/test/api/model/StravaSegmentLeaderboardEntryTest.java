package test.api.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.model.reference.StravaGender;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaSegmentLeaderboardEntryTest extends BeanTest<StravaSegmentLeaderboardEntry> {

	public static void validate(final StravaSegmentLeaderboardEntry entry) {
		assertNotNull(entry);
		assertNotNull(entry.getActivityId());
		if (entry.getAthleteGender() != null) {
			assertFalse(entry.getAthleteGender() == StravaGender.UNKNOWN);
		}
		assertNotNull(entry.getAthleteName());
		assertNotNull(entry.getAthleteProfile());
		if (entry.getAverageHr() != null) {
			assertTrue(entry.getAverageHr() >= 0);
		}
		if (entry.getAverageWatts() != null) {
			assertTrue(entry.getAverageWatts() >= 0);
		}
		assertNotNull(entry.getDistance());
		assertTrue(entry.getDistance() >= 0);
		assertNotNull(entry.getEffortId());
		assertNotNull(entry.getElapsedTime());
		assertTrue(entry.getElapsedTime() >= 0);
		assertNotNull(entry.getMovingTime());
		assertTrue(entry.getMovingTime() >= 0);
		assertTrue(entry.getElapsedTime() >= entry.getMovingTime());
		assertNotNull(entry.getRank());
		assertTrue(entry.getRank() > 0);
		assertNotNull(entry.getStartDate());
		assertNotNull(entry.getStartDateLocal());
	}

	@Override
	protected Class<StravaSegmentLeaderboardEntry> getClassUnderTest() {
		return StravaSegmentLeaderboardEntry.class;
	}
}
