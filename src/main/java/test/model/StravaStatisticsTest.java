package test.model;

import javastrava.model.StravaStatistics;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaStatistics}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaStatisticsTest extends BeanTest<StravaStatistics> {

	@Override
	protected Class<StravaStatistics> getClassUnderTest() {
		return StravaStatistics.class;
	}

}
