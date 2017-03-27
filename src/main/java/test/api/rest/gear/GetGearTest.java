package test.api.rest.gear;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import test.api.rest.APIGetTest;
import test.api.rest.callback.APIGetCallback;
import test.service.standardtests.data.GearDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#getGear(String)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetGearTest extends APIGetTest<StravaGear, String> {
	@Override
	protected APIGetCallback<StravaGear, String> getter() {
		return ((api, id) -> api.getGear(id));
	}

	@Override
	protected String invalidId() {
		return GearDataUtils.GEAR_INVALID_ID;
	}

	@Override
	protected String privateId() {
		return null;
	}

	@Override
	protected String privateIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * Test getting gear that belongs to another athlete - should fail with a {@link NotFoundException}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetGear_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getGear(GearDataUtils.GEAR_OTHER_ATHLETE_ID);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got gear details for gear belonging to another athlete!"); //$NON-NLS-1$
		});
	}

	@Override
	protected void validate(final StravaGear result) throws Exception {
		GearDataUtils.validateGear(result);

	}

	@Override
	protected String validId() {
		return GearDataUtils.GEAR_VALID_ID;
	}

	@Override
	protected String validIdBelongsToOtherUser() {
		return null;
	}
}
