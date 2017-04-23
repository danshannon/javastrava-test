package test.api.rest.clubgroupevent.async;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import test.api.rest.callback.APIListCallback;
import test.api.rest.clubgroupevent.ListEventJoinedAthletesTest;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.ClubGroupEventDataUtils;

/**
 * Test and configuration for {@link API#listEventJoinedAthletesAsync(Integer, Integer, Integer)}
 *
 * @author Dan Shannon
 *
 */
public class ListEventJoinedAthletesAsyncTest extends ListEventJoinedAthletesTest {

	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listEventJoinedAthletesAsync(id, null, null).get();
	}

	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listEventJoinedAthletesAsync(ClubGroupEventDataUtils.CLUB_EVENT_VALID_ID, paging.getPage(), paging.getPageSize()).get();
	}

}
