package test.api.model;

import javastrava.api.v3.model.StravaAthleteZone;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaAthleteZoneTest}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaAthleteZoneTest extends BeanTest<StravaAthleteZone> {
	@Override
	protected Class<StravaAthleteZone> getClassUnderTest() {
		return StravaAthleteZone.class;
	}

}
