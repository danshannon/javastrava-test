package test.api.model;

import javastrava.api.v3.model.StravaAthleteZones;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaAthleteZonesTest}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaAthleteZonesTest extends BeanTest<StravaAthleteZones> {
	@Override
	protected Class<StravaAthleteZones> getClassUnderTest() {
		return StravaAthleteZones.class;
	}

}
