package test.api.rest.athlete.async;

import java.util.Arrays;

import javastrava.api.v3.model.StravaSegmentEffort;
import test.api.model.StravaSegmentEffortTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.utils.TestUtils;

public class ListAthleteKOMsAsyncTest extends APIPagingListTest<StravaSegmentEffort, Integer> {
	/**
	 * @see test.api.rest.APIPagingListTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaSegmentEffort> pagingCallback() {
		return paging -> api().listAthleteKOMsAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}

	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaSegmentEffort, Integer> listCallback() {
		return (api, id) -> api.listAthleteKOMsAsync(id, null, null).get();
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.ATHLETE_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		StravaSegmentEffortTest.validateSegmentEffort(effort);
	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaSegmentEffort[] list) {
		StravaSegmentEffortTest.validateList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return TestUtils.ATHLETE_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return TestUtils.ATHLETE_WITHOUT_FRIENDS;
	}

}
