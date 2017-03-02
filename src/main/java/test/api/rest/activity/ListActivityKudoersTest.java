package test.api.rest.activity;

import java.util.Arrays;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.utils.TestUtils;

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
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return TestUtils.ACTIVITY_PRIVATE_WITH_KUDOS;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaAthlete[] bigList) {
		StravaAthleteTest.validateList(Arrays.asList(bigList));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Long validId() {
		return TestUtils.ACTIVITY_WITH_KUDOS;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Long validIdNoChildren() {
		return TestUtils.ACTIVITY_WITHOUT_KUDOS;
	}

}
