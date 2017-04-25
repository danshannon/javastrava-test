package test.api.runningrace.async;

import javastrava.api.API;
import javastrava.model.StravaRunningRace;
import test.api.callback.APIGetCallback;
import test.api.runningrace.GetRaceTest;

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
