package test.api.model;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaAthleteZone;
import test.utils.BeanTest;

public class StravaAthleteZoneTest extends BeanTest<StravaAthleteZone> {
	public static void validate(StravaAthleteZone zone) {
		assertNotNull(zone.getZones());
	}

	@Override
	protected Class<StravaAthleteZone> getClassUnderTest() {
		return StravaAthleteZone.class;
	}

}
