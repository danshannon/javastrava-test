package test.api.rest.runningrace;

import javastrava.api.v3.model.StravaRunningRace;
import test.api.model.StravaRunningRaceTest;
import test.api.rest.APIListTest;
import test.api.rest.TestListArrayCallback;
import test.utils.TestUtils;

public class ListRacesTest extends APIListTest<StravaRunningRace, Integer> {

	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaRunningRace, Integer> listCallback() {
		return (api, id) -> api.listRaces(null);
	}

	@Override
	protected Integer invalidId() {
		return TestUtils.RUNNING_RACE_INVALID_ID;
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
	protected void validateArray(StravaRunningRace[] list) throws Exception {
		for (final StravaRunningRace race : list) {
			validate(race);
		}

	}

	@Override
	protected Integer validId() {
		return TestUtils.RUNNING_RACE_VALID_ID;
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
	protected void validate(StravaRunningRace result) throws Exception {
		StravaRunningRaceTest.validate(result);

	}

}
