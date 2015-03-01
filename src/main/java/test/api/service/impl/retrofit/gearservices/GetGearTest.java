package test.api.service.impl.retrofit.gearservices;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.service.GearServices;
import javastrava.api.v3.service.impl.retrofit.GearServicesImpl;

import org.junit.Test;

import test.api.model.StravaGearTest;
import test.utils.TestUtils;

public class GetGearTest {
	// Test cases
	// 1. Valid gear
	// 2. Invalid gear
	// 3. StravaGear which doesn't belong to the current athlete
	@Test
	public void testGetGear_validGear() {
		final GearServices service = getGearService();
		final StravaGear gear = service.getGear(TestUtils.GEAR_VALID_ID);

		assertNotNull(gear);
		StravaGearTest.validateGear(gear, TestUtils.GEAR_VALID_ID, gear.getResourceState());
	}

	@Test
	public void testGetGear_invalidGear() {
		final GearServices service = getGearService();
		final StravaGear gear = service.getGear(TestUtils.GEAR_INVALID_ID);

		assertNull(gear);
	}

	@Test
	public void testGetGear_otherAthlete() {
		final GearServices service = getGearService();
		final StravaGear gear = service.getGear(TestUtils.GEAR_OTHER_ATHLETE_ID);

		assertNull(gear);
	}

	@Test
	public void testGetGear_privateAthlete() {
		final StravaGear gear = getGearService().getGear(TestUtils.GEAR_OTHER_ATHLETE_ID);
		assertNull(gear);
	}

	private GearServices getGearService() {
		return GearServicesImpl.implementation(TestUtils.getValidToken());
	}

}
