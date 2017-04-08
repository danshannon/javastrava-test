package test.service.impl.gearservice;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaGear;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.GearDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for getGear methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetGearTest extends GetMethodTest<StravaGear, String> {
	@Override
	protected String getIdInvalid() {
		return GearDataUtils.GEAR_INVALID_ID;
	}

	@Override
	protected String getIdPrivate() {
		return null;
	}

	@Override
	protected String getIdPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected String getIdValid() {
		return GearDataUtils.GEAR_VALID_ID;
	}

	@Override
	protected GetCallback<StravaGear, String> getter() {
		return ((strava, id) -> strava.getGear(id));
	}

	/**
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetGear_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaGear gear = TestUtils.strava().getGear(GearDataUtils.GEAR_OTHER_ATHLETE_ID);

			assertNull(gear);
		});
	}

	@Override
	protected void validate(StravaGear object) {
		GearDataUtils.validateGear(object);

	}

}
