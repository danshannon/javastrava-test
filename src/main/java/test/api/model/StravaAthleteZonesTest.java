package test.api.model;

import static org.junit.Assert.assertNotNull;

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
	/**
	 * @param zones
	 *            Zones to be validated
	 */
	public static void validate(StravaAthleteZones zones) {
		assertNotNull(zones.getHeartRate());
	}

	@Override
	protected Class<StravaAthleteZones> getClassUnderTest() {
		return StravaAthleteZones.class;
	}

}
