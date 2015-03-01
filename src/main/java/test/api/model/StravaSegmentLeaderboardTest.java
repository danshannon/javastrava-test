package test.api.model;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaSegmentLeaderboardTest extends BeanTest<StravaSegmentLeaderboard> {

	@Override
	protected Class<StravaSegmentLeaderboard> getClassUnderTest() {
		return StravaSegmentLeaderboard.class;
	}

	public static void validate(final StravaSegmentLeaderboard leaderboard) {
		assertNotNull(leaderboard);
		assertNotNull(leaderboard.getAthleteEntries());
		assertNotNull(leaderboard.getEffortCount());
		assertNotNull(leaderboard.getEntries());
		assertNotNull(leaderboard.getEntryCount());
		// TODO Apparently optional but see https://github.com/danshannon/javastravav3api/issues/22
		// assertNotNull(leaderboard.getKomType());
		assertNotNull(leaderboard.getNeighborhoodCount());
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getAthleteEntries()) {
			StravaSegmentLeaderboardEntryTest.validate(entry);
		}
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			StravaSegmentLeaderboardEntryTest.validate(entry);
		}
	}
}
