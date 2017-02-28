package test.api.rest.gear.async;

import static org.junit.Assert.fail;

import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.service.exception.NotFoundException;
import test.api.rest.TestGetCallback;
import test.api.rest.gear.GetGearTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetGearAsyncTest extends GetGearTest {
	@Override
	public void testGetGear_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getGearAsync(TestUtils.GEAR_OTHER_ATHLETE_ID).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got gear details for gear belonging to another athlete!");
		});
	}

	@Override
	protected TestGetCallback<StravaGear, String> getCallback() {
		return ((api, id) -> api.getGearAsync(id).get());
	}

}
