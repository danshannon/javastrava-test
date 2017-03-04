package test.api.model;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaStatistics;
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

	/**
	 * Validate the structure and content of a statistics object
	 * 
	 * @param stats
	 *            The object to be validated
	 */
	public static void validate(final StravaStatistics stats) {
		assertNotNull(stats);

	}

	@Override
	protected Class<StravaStatistics> getClassUnderTest() {
		return StravaStatistics.class;
	}

}
