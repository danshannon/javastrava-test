package test.api.activity;

import static org.junit.Assume.assumeFalse;

import javastrava.api.API;
import javastrava.model.StravaAthlete;
import test.api.APIPagingListTest;
import test.api.callback.APIListCallback;
import test.api.util.ArrayCallback;
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
	 * @see test.api.APIListTest#invalidId()
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
	 * @see test.api.APIListTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_WITH_KUDOS;
	}

	/**
	 * @see test.api.APIListTest#privateIdBelongsToOtherUser()
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
	 * @see test.api.APIListTest#validId()
	 */
	@Override
	protected Long validId() {
		return ActivityDataUtils.ACTIVITY_WITH_KUDOS;
	}

	/**
	 * @see test.api.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Long validIdNoChildren() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_KUDOS;
	}

}
