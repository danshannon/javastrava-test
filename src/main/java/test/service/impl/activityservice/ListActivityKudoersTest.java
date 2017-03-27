package test.service.impl.activityservice;

import javastrava.api.v3.model.StravaAthlete;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.AthleteDataUtils;

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
	protected Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_WITH_KUDOS;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_KUDOS;
	}

	@Override
	protected ListCallback<StravaAthlete, Long> lister() {
		return ((strava, id) -> strava.listActivityKudoers(id));
	}

	@Override
	protected PagingListCallback<StravaAthlete, Long> pagingLister() {
		return ((strava, paging, id) -> strava.listActivityKudoers(id, paging));
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		AthleteDataUtils.validateAthlete(athlete, athlete.getId(), athlete.getResourceState());
	}
}
