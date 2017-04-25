package test.api.clubgroupevent.async;

import javastrava.api.API;
import javastrava.model.StravaAthlete;
import test.api.callback.APIListCallback;
import test.api.clubgroupevent.ListEventJoinedAthletesTest;
import test.api.util.ArrayCallback;
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
