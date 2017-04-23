package test.api.rest.club;

import javastrava.api.v3.model.StravaAthlete;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.ClubDataUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAdminsTest extends APIPagingListTest<StravaAthlete, Integer> {

	@Override
	protected Integer invalidId() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listClubAdmins(ClubDataUtils.CLUB_VALID_ID, paging.getPage(), paging.getPageSize());
	}

	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listClubAdmins(id, null, null);
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validateArray(StravaAthlete[] list) throws Exception {
		for (final StravaAthlete athlete : list) {
			AthleteDataUtils.validateAthlete(athlete);
		}

	}

	@Override
	protected Integer validId() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

	@Override
	protected void validate(StravaAthlete result) throws Exception {
		AthleteDataUtils.validateAthlete(result);

	}

}
