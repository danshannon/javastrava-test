package test.api.clubgroupevent;

import javastrava.api.API;
import javastrava.model.StravaClubEvent;
import test.api.APIGetTest;
import test.api.callback.APIGetCallback;
import test.service.standardtests.data.ClubGroupEventDataUtils;

/**
 * <p>
 * Specific tests for {@link API#getEvent(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetEventTest extends APIGetTest<StravaClubEvent, Integer> {

	@Override
	protected APIGetCallback<StravaClubEvent, Integer> getter() {
		return ((api, id) -> api.getEvent(id));
	}

	@Override
	protected Integer invalidId() {
		return ClubGroupEventDataUtils.CLUB_EVENT_INVALID_ID;
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
	protected void validate(StravaClubEvent event) throws Exception {
		ClubGroupEventDataUtils.validateEvent(event);

	}

	@Override
	protected Integer validId() {
		return ClubGroupEventDataUtils.CLUB_EVENT_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
