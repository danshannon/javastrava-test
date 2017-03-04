package test.api.model;

import javastrava.api.v3.model.StravaStatisticsEntry;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaStatisticsEntry}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaStatisticsEntryTest extends BeanTest<StravaStatisticsEntry> {

	@Override
	protected Class<StravaStatisticsEntry> getClassUnderTest() {
		return StravaStatisticsEntry.class;
	}

}
