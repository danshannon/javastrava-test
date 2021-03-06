package test.service.impl.runningraceservice;

import javastrava.model.StravaRunningRace;
import javastrava.service.Strava;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.RunningRaceDataUtils;

/**
 * Tests for {@link Strava#getRace(Integer)} method
 *
 * @author Dan Shannon
 *
 */
public class GetRaceTest extends GetMethodTest<StravaRunningRace, Integer> {

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
	protected Integer getIdValid() {
		return RunningRaceDataUtils.RUNNING_RACE_VALID_ID;
	}

	@Override
	protected GetCallback<StravaRunningRace, Integer> getter() {
		return (strava, id) -> strava.getRace(id);
	}

	@Override
	protected void validate(StravaRunningRace object) {
		RunningRaceDataUtils.validateRace(object);
	}

}
