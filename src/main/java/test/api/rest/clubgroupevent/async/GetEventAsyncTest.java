package test.api.rest.clubgroupevent.async;

import javastrava.api.v3.model.StravaClubEvent;
import javastrava.api.v3.rest.API;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.clubgroupevent.GetEventTest;

/**
 * <p>
 * Specific tests and config for {@link API#getEventAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetEventAsyncTest extends GetEventTest {

	@Override
	protected APIGetCallback<StravaClubEvent, Integer> getter() {
		return ((api, id) -> api.getEventAsync(id).get());
	}

}
