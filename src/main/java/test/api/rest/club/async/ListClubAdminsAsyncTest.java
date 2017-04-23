package test.api.rest.club.async;

import javastrava.api.v3.model.StravaAthlete;
import test.api.rest.callback.APIListCallback;
import test.api.rest.club.ListClubAdminsTest;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.ClubDataUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAdminsAsyncTest extends ListClubAdminsTest {

	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listClubAdminsAsync(id, null, null).get();
	}

	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listClubAdminsAsync(ClubDataUtils.CLUB_VALID_ID, paging.getPage(), paging.getPageSize()).get();
	}

}
