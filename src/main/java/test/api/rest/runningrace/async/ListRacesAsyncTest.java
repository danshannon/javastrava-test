package test.api.rest.runningrace.async;

import javastrava.api.v3.model.StravaRunningRace;
import javastrava.api.v3.rest.API;
import test.api.rest.callback.APIListCallback;
import test.api.rest.runningrace.ListRacesTest;

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
