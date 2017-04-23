package test.api.rest.clubgroupevent;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.ClubGroupEventDataUtils;

/**
 * <p>
 * Specific tests and configuration for {@link API#listEventJoinedAthletes}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListEventJoinedAthletesTest extends APIPagingListTest<StravaAthlete, Integer> {

	@Override
	protected Integer invalidId() {
		return ClubGroupEventDataUtils.CLUB_EVENT_INVALID_ID;
	}

	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listEventJoinedAthletes(id, null, null);
	}

	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return (paging -> api().listEventJoinedAthletes(ClubGroupEventDataUtils.CLUB_EVENT_VALID_ID, paging.getPage(), paging.getPageSize()));
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
	protected void validate(StravaAthlete athlete) throws Exception {
		AthleteDataUtils.validateAthlete(athlete);

	}

	@Override
	protected void validateArray(StravaAthlete[] list) throws Exception {
		for (final StravaAthlete athlete : list) {
			validate(athlete);
		}

	}

	@Override
	protected Integer validId() {
		return ClubGroupEventDataUtils.CLUB_EVENT_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
