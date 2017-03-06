package test.api.rest.gear;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.service.exception.NotFoundException;
import test.api.model.StravaGearTest;
import test.api.rest.APIGetTest;
import test.api.rest.callback.TestGetCallback;
import test.service.standardtests.data.GearDataUtils;
import test.utils.RateLimitedTestRunner;

public class GetGearTest extends APIGetTest<StravaGear, String> {
	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected String invalidId() {
		return GearDataUtils.GEAR_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected String privateId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected String privateIdBelongsToOtherUser() {
		return null;
	}

	@Test
	public void testGetGear_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getGear(GearDataUtils.GEAR_OTHER_ATHLETE_ID);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got gear details for gear belonging to another athlete!");
		});
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaGear result) throws Exception {
		StravaGearTest.validateGear(result);

	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected String validId() {
		return GearDataUtils.GEAR_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected String validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected TestGetCallback<StravaGear, String> getter() {
		return ((api, id) -> api.getGear(id));
	}

}
