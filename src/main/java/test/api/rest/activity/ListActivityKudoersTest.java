package test.api.rest.activity;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Tests for {@link Strava#listActivityKudoers(Long)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityKudoersTest extends APIPagingListTest<StravaAthlete, Long> {
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return (paging -> api().listActivityKudoers(validId(), paging.getPage(), paging.getPageSize()));
	}

	@Override
	protected TestListArrayCallback<StravaAthlete, Long> listCallback() {
		return ((api, id) -> api.listActivityKudoers(id, null, null));
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_WITH_KUDOS;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaAthlete[] athletes) {
		for (final StravaAthlete athlete : athletes) {
			StravaAthleteTest.validateAthlete(athlete);
		}
	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Long validId() {
		return ActivityDataUtils.ACTIVITY_WITH_KUDOS;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Long validIdNoChildren() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_KUDOS;
	}

}
