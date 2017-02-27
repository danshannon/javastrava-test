package test.api.service.impl.activityservice;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.api.service.standardtests.PagingListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.api.service.standardtests.callbacks.PagingListCallback;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for methods dealing with listing kudos on activities
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityKudoersTest extends PagingListMethodTest<StravaAthlete, Long> {
	@Override
	protected PagingListCallback<StravaAthlete, Long> pagingLister() {
		return ((strava, paging, id) -> strava.listActivityKudoers(id, paging));
	}

	@Override
	protected ListCallback<StravaAthlete, Long> lister() {
		return ((strava, id) -> strava.listActivityKudoers(id));
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete, athlete.getId(), athlete.getResourceState());
	}

	@Override
	protected Long idPrivate() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return TestUtils.ACTIVITY_WITH_KUDOS;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return TestUtils.ACTIVITY_WITHOUT_KUDOS;
	}

	@Override
	protected Long idInvalid() {
		return TestUtils.ACTIVITY_INVALID;
	}
}
