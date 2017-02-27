package test.api.service.impl.athleteservice;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.api.service.standardtests.PagingListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.api.service.standardtests.callbacks.PagingListCallback;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for list athlete friends methods
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class ListAthleteFriendsTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Override
	protected PagingListCallback<StravaAthlete, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listAthleteFriends(id, paging));
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete);
	}

	@Override
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listAthleteFriends(id));
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return TestUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected Integer idValidWithEntries() {
		return TestUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return TestUtils.ATHLETE_WITHOUT_FRIENDS;
	}

	@Override
	protected Integer idInvalid() {
		return TestUtils.ATHLETE_INVALID_ID;
	}

}
