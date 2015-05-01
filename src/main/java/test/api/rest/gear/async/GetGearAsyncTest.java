package test.api.rest.gear.async;

import static org.junit.Assert.fail;

import javastrava.api.v3.service.exception.NotFoundException;
import test.api.rest.gear.GetGearTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetGearAsyncTest extends GetGearTest {
	/**
	 *
	 */
	public GetGearAsyncTest() {
		this.getCallback = (api, id) -> api.getGearAsync(id).get();
	}

	@Override
	public void testGetGear_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getGear(TestUtils.GEAR_OTHER_ATHLETE_ID);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got gear details for gear belonging to another athlete!");
		} );
	}

}
