package test.api.model;

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

	@Override
	protected Class<StravaAthleteSegmentStats> getClassUnderTest() {
		return StravaAthleteSegmentStats.class;
	}

}
