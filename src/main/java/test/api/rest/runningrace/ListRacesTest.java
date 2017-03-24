package test.api.rest.runningrace;

import javastrava.api.v3.model.StravaRunningRace;
import javastrava.api.v3.rest.API;
import test.api.model.StravaRunningRaceTest;
import test.api.rest.APIListTest;
import test.api.rest.callback.APIListCallback;
import test.service.standardtests.data.RunningRaceDataUtils;

/**
 * <p>
 * Specific config and tests for {@link API#listRaces(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListRacesTest extends APIListTest<StravaRunningRace, Integer> {

	@Override
	protected Integer invalidId() {
		return RunningRaceDataUtils.RUNNING_RACE_INVALID_ID;
	}

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
