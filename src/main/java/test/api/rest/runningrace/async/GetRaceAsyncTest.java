package test.api.rest.runningrace.async;

import javastrava.api.v3.model.StravaRunningRace;
import javastrava.api.v3.rest.API;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.runningrace.GetRaceTest;

/**
 * <p>
 * Specific tests and config for {@link API#getRaceAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetRaceAsyncTest extends GetRaceTest {
	@Override
	protected APIGetCallback<StravaRunningRace, Integer> getter() {
		return ((api, id) -> api.getRaceAsync(id).get());
	}

}
