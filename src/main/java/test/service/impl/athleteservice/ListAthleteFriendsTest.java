package test.service.impl.athleteservice;

import javastrava.api.v3.model.StravaAthlete;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.AthleteDataUtils;

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
	protected Integer idInvalid() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return AthleteDataUtils.ATHLETE_WITHOUT_FRIENDS;
	}

	@Override
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listAthleteFriends(id));
	}

	@Override
	protected PagingListCallback<StravaAthlete, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listAthleteFriends(id, paging));
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		AthleteDataUtils.validateAthlete(athlete);
	}

	@Override
	protected Class<StravaAthlete> classUnderTest() {
		return StravaAthlete.class;
	}

}
