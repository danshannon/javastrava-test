package test.api.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javastrava.api.v3.model.StravaAthleteSegmentStats;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaAthleteSegmentStats}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaAthleteSegmentStatsTest extends BeanTest<StravaAthleteSegmentStats> {

	/**
	 * <p>
	 * Validate structure and content
	 * </p>
	 *
	 * @param stats
	 *            Stats to be validated
	 */
	@SuppressWarnings("boxing")
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
