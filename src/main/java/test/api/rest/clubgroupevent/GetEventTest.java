package test.api.rest.clubgroupevent;

import javastrava.api.v3.model.StravaClubEvent;
import javastrava.api.v3.rest.API;
import test.api.rest.APIGetTest;
import test.api.rest.callback.APIGetCallback;
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
