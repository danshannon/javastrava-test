package test.api.model;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaAthleteZones;
import test.utils.BeanTest;

public class StravaAthleteZonesTest extends BeanTest<StravaAthleteZones> {
	public static void validate(StravaAthleteZones zones) {
		assertNotNull(zones.getHeartRate());
	}

	@Override
	protected Class<StravaAthleteZones> getClassUnderTest() {
		return StravaAthleteZones.class;
	}

}
