package test.api.rest.gear.async;

import static org.junit.Assert.fail;

import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import test.api.rest.callback.TestGetCallback;
import test.api.rest.gear.GetGearTest;
import test.service.standardtests.data.GearDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Tests for {@link API#getGearAsync(String)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetGearAsyncTest extends GetGearTest {
	@SuppressWarnings("nls")
	@Override
	public void testGetGear_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getGearAsync(GearDataUtils.GEAR_OTHER_ATHLETE_ID).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got gear details for gear belonging to another athlete!");
		});
	}

	@Override
	protected TestGetCallback<StravaGear, String> getter() {
		return ((api, id) -> api.getGearAsync(id).get());
	}

}
