package test.service.impl.runningraceservice;

import javastrava.api.v3.model.StravaRunningRace;
import test.api.model.StravaRunningRaceTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.RunningRaceDataUtils;

public class ListRacesTest extends ListMethodTest<StravaRunningRace, Integer> {

	@Override
	protected Integer idInvalid() {
		return null;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return RunningRaceDataUtils.RUNNING_RACE_VALID_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaRunningRace, Integer> lister() {
		return (strava, id) -> strava.listRaces(null);
	}

	@Override
	protected void validate(StravaRunningRace object) {
		StravaRunningRaceTest.validate(object);
	}

}
