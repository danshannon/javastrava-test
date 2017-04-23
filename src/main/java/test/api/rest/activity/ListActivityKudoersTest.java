package test.api.rest.activity;

import static org.junit.Assume.assumeFalse;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue161;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Tests for {@link API#listActivityKudoers(Long, Integer, Integer)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityKudoersTest extends APIPagingListTest<StravaAthlete, Long> {
	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	public void list_privateWithoutViewPrivate() throws Exception {
		assumeFalse(Issue161.issue());

		super.list_privateWithoutViewPrivate();
	}

	@Override
	protected APIListCallback<StravaAthlete, Long> listCallback() {
		return ((api, id) -> api.listActivityKudoers(id, null, null));
	}

	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return (paging -> api().listActivityKudoers(validId(), paging.getPage(), paging.getPageSize()));
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
		AthleteDataUtils.validateAthlete(athlete);

	}

	@Override
	protected void validateArray(final StravaAthlete[] athletes) {
		for (final StravaAthlete athlete : athletes) {
			AthleteDataUtils.validateAthlete(athlete);
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
