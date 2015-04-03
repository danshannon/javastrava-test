package test.api.rest.gear;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.service.exception.NotFoundException;

import org.junit.Test;

import test.api.model.StravaGearTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetGearTest extends APITest {
	@Test
	public void testGetGear_invalidGear() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getGear(TestUtils.GEAR_INVALID_ID);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned gear for a non-existent piece of gear");
		});
	}

	@Test
	public void testGetGear_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getGear(TestUtils.GEAR_OTHER_ATHLETE_ID);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got gear details for gear belonging to another athlete!");
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
