package test.api.rest.gear;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaGear;

import org.junit.Test;

import test.api.model.StravaGearTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetGearTest extends APITest {
	@Test
	public void testGetGear_invalidGear() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaGear gear = api().getGear(TestUtils.GEAR_INVALID_ID);

			assertNull(gear);
		});
	}

	@Test
	public void testGetGear_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaGear gear = api().getGear(TestUtils.GEAR_OTHER_ATHLETE_ID);

			assertNull(gear);
		});
	}

	@Test
	public void testGetGear_validGear() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaGear gear = api().getGear(TestUtils.GEAR_VALID_ID);

			assertNotNull(gear);
			StravaGearTest.validateGear(gear, TestUtils.GEAR_VALID_ID, gear.getResourceState());
		});
	}

}
