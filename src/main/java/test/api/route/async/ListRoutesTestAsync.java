package test.api.route.async;

import javastrava.api.API;
import javastrava.model.StravaRoute;
import test.api.callback.APIListCallback;
import test.api.route.ListRoutesTest;

/**
 * <p>
 * Tests for {@link API#listAthleteRoutesAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListRoutesTestAsync extends ListRoutesTest {
	@Override
	protected APIListCallback<StravaRoute, Integer> listCallback() {
		return ((api, id) -> api.listAthleteRoutesAsync(id).get());
	}

}
