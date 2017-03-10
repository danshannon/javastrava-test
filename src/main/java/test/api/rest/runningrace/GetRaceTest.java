package test.api.rest.runningrace;

import javastrava.api.v3.model.StravaRunningRace;
import test.api.model.StravaRunningRaceTest;
import test.api.rest.APIGetTest;
import test.api.rest.callback.APIGetCallback;
import test.service.standardtests.data.RunningRaceDataUtils;

/**
 * <p>
 * Specific tests for getRace methods
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class GetRaceTest extends APIGetTest<StravaRunningRace, Integer> {

	@Override
	protected APIGetCallback<StravaRunningRace, Integer> getter() {
		return ((api, id) -> api.getRace(id));
	}

	@Override
	protected Integer invalidId() {
		return RunningRaceDataUtils.RUNNING_RACE_INVALID_ID;
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
	protected Integer validId() {
		return RunningRaceDataUtils.RUNNING_RACE_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(StravaRunningRace result) throws Exception {
		StravaRunningRaceTest.validate(result);
	}

}
