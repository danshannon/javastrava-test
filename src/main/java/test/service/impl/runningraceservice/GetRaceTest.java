package test.service.impl.runningraceservice;

import javastrava.api.v3.model.StravaRunningRace;
import test.api.model.StravaRunningRaceTest;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.RunningRaceDataUtils;

public class GetRaceTest extends GetMethodTest<StravaRunningRace, Integer> {

	@Override
	protected Integer getIdValid() {
		return RunningRaceDataUtils.RUNNING_RACE_VALID_ID;
	}

	@Override
	protected Integer getIdInvalid() {
		return RunningRaceDataUtils.RUNNING_RACE_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return null;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected GetCallback<StravaRunningRace, Integer> getter() throws Exception {
		return (strava, id) -> strava.getRace(id);
	}

	@Override
	protected void validate(StravaRunningRace object) {
		StravaRunningRaceTest.validate(object);
	}

}
