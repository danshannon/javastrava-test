package test.api.model;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSegmentLeaderboard}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentLeaderboardTest extends BeanTest<StravaSegmentLeaderboard> {

	/**
	 * Validate the structure and content of a leaderboard
	 *
	 * @param leaderboard
	 *            the leaderboard to be validated
	 */
	@SuppressWarnings("boxing")
	public static void validate(final StravaSegmentLeaderboard leaderboard) {
		assertNotNull(leaderboard);
		// Optional (if using API only) assertNotNull(leaderboard.getAthleteEntries());
		// Optional assertNotNull(leaderboard.getEffortCount());
		assertNotNull(leaderboard.getEntryCount());
		if (leaderboard.getEntryCount() != 0) {
			assertNotNull(leaderboard.getEntries());
		}
		// TODO Apparently optional but see https://github.com/danshannon/javastravav3api/issues/22
		// assertNotNull(leaderboard.getKomType());
		assertNotNull(leaderboard.getNeighborhoodCount());
		if (leaderboard.getAthleteEntries() != null) {
			for (final StravaSegmentLeaderboardEntry entry : leaderboard.getAthleteEntries()) {
				StravaSegmentLeaderboardEntryTest.validate(entry);
			}
		}
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			StravaSegmentLeaderboardEntryTest.validate(entry);
		}
	}

	@Override
	protected Class<StravaSegmentLeaderboard> getClassUnderTest() {
		return StravaSegmentLeaderboard.class;
	}
}
