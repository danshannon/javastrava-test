package test.api.runningrace.async;

import javastrava.api.API;
import javastrava.model.StravaRunningRace;
import test.api.callback.APIListCallback;
import test.api.runningrace.ListRacesTest;

/**
 * <p>
 * Specific config and tests for {@link API#listRacesAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListRacesAsyncTest extends ListRacesTest {
	@Override
	protected APIListCallback<StravaRunningRace, Integer> listCallback() {
		return (api, id) -> api.listRacesAsync(null).get();
	}

}
