package test.api.service.impl.gearservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaGear;

import org.junit.Test;

import test.api.model.StravaGearTest;
import test.api.service.StravaTest;
import test.utils.TestUtils;

public class GetGearTest extends StravaTest {
	// Test cases
	// 1. Valid gear
	// 2. Invalid gear
	// 3. StravaGear which doesn't belong to the current athlete
	@Test
	public void testGetGear_validGear() {
		final StravaGear gear = service().getGear(TestUtils.GEAR_VALID_ID);

		assertNotNull(gear);
		StravaGearTest.validateGear(gear, TestUtils.GEAR_VALID_ID, gear.getResourceState());
	}

	@Test
	public void testGetGear_invalidGear() {
		final StravaGear gear = service().getGear(TestUtils.GEAR_INVALID_ID);

		assertNull(gear);
	}

	@Test
	public void testGetGear_otherAthlete() {
		final StravaGear gear = service().getGear(TestUtils.GEAR_OTHER_ATHLETE_ID);

		assertNull(gear);
	}

	@Test
	public void testGetGear_privateAthlete() {
		final StravaGear gear = service().getGear(TestUtils.GEAR_OTHER_ATHLETE_ID);
		assertNull(gear);
	}

}
