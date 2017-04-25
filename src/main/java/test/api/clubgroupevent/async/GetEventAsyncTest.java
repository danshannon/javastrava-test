package test.api.clubgroupevent.async;

import javastrava.api.API;
import javastrava.model.StravaClubEvent;
import test.api.callback.APIGetCallback;
import test.api.clubgroupevent.GetEventTest;

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
