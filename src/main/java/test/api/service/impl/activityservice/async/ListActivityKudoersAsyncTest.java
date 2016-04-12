package test.api.service.impl.activityservice.async;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaAthleteTest;
import test.api.service.standardtests.async.AsyncPagingListMethodTest;
import test.api.service.standardtests.callbacks.AsyncListCallback;
import test.utils.TestUtils;

public class ListActivityKudoersAsyncTest extends AsyncPagingListMethodTest<StravaAthlete, Integer> {
	@Override
	protected AsyncListCallback<StravaAthlete> deleter() {
		return (paging -> strava().listActivityKudoersAsync(TestUtils.ACTIVITY_WITH_KUDOS, paging));
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		validate(athlete, athlete.getId(), athlete.getResourceState());

	}

	@Override
	protected void validate(final StravaAthlete athlete, final Integer id, final StravaResourceState state) {
		StravaAthleteTest.validateAthlete(athlete, id, state);

	}
}
