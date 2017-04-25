package test.api.runningrace;

import javastrava.api.API;
import javastrava.model.StravaRunningRace;
import test.api.APIListTest;
import test.api.callback.APIListCallback;
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
		return null;
	}

	@Override
	protected APIListCallback<StravaRunningRace, Integer> listCallback() {
		return (api, id) -> api.listRaces(id);
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
		RunningRaceDataUtils.validateRace(result);

	}

	@Override
	protected void validateArray(StravaRunningRace[] list) throws Exception {
		for (final StravaRunningRace race : list) {
			validate(race);
		}

	}

	@Override
	protected Integer validId() {
		return RunningRaceDataUtils.RUNNING_RACE_VALID_YEAR;
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
