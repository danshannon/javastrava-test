package test.api.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaAthleteSegmentStats;
import test.utils.BeanTest;

public class StravaAthleteSegmentStatsTest extends BeanTest<StravaAthleteSegmentStats> {

	public static void validate(final StravaAthleteSegmentStats stats) {
		assertNotNull(stats);
		assertNotNull(stats.getEffortCount());
		assertTrue(stats.getEffortCount() > 0);
		assertNotNull(stats.getPrElapsedTime());
		assertTrue(stats.getPrElapsedTime() > 0);
		assertNotNull(stats.getPrDate());
	}

	@Override
	protected Class<StravaAthleteSegmentStats> getClassUnderTest() {
		return StravaAthleteSegmentStats.class;
	}

}
