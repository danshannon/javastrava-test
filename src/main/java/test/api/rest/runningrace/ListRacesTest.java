package test.api.rest.runningrace;

import javastrava.api.v3.model.StravaRunningRace;
import test.api.model.StravaRunningRaceTest;
import test.api.rest.APIListTest;
import test.api.rest.callback.APIListCallback;
import test.service.standardtests.data.RunningRaceDataUtils;

public class ListRacesTest extends APIListTest<StravaRunningRace, Integer> {

	@Override
	protected Integer invalidId() {
		return RunningRaceDataUtils.RUNNING_RACE_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaRunningRace, Integer> listCallback() {
		return (api, id) -> api.listRaces(null);
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
	protected void validate(StravaRunningRace result) throws Exception {
		StravaRunningRaceTest.validate(result);

	}

	@Override
	protected void validateArray(StravaRunningRace[] list) throws Exception {
		for (final StravaRunningRace race : list) {
			validate(race);
		}

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
	protected Integer validIdNoChildren() {
		return null;
	}

}
