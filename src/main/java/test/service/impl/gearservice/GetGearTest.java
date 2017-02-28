package test.service.impl.gearservice;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaGear;
import test.api.model.StravaGearTest;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
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
	/**
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetGear_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaGear gear = TestUtils.strava().getGear(TestUtils.GEAR_OTHER_ATHLETE_ID);

			assertNull(gear);
		});
	}

	@Override
	protected String getIdValid() {
		return TestUtils.GEAR_VALID_ID;
	}

	@Override
	protected String getIdInvalid() {
		return TestUtils.GEAR_INVALID_ID;
	}

	@Override
	protected String getIdPrivate() {
		return null;
	}

	@Override
	protected String getIdPrivateBelongsToOtherUser() {
		return TestUtils.GEAR_OTHER_ATHLETE_ID;
	}

	@Override
	protected GetCallback<StravaGear, String> getter() throws Exception {
		return ((strava, id) -> strava.getGear(id));
	}

	@Override
	protected void validate(StravaGear object) {
		StravaGearTest.validateGear(object);

	}

}
