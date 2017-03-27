package test.api.rest.athlete.async;

import java.util.Arrays;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.rest.API;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.SegmentEffortDataUtils;

/**
 * <p>
 * Specific tests for {@link API#listAthleteKOMsAsync(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAthleteKOMsAsyncTest extends APIPagingListTest<StravaSegmentEffort, Integer> {
	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaSegmentEffort, Integer> listCallback() {
		return (api, id) -> api.listAthleteKOMsAsync(id, null, null).get();
	}

	/**
	 * @see test.api.rest.APIPagingListTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaSegmentEffort> pagingCallback() {
		return paging -> api().listAthleteKOMsAsync(validId(), paging.getPage(), paging.getPageSize()).get();
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
		SegmentEffortDataUtils.validateSegmentEffort(effort);
	}

	@Override
	protected void validateArray(final StravaSegmentEffort[] list) {
		SegmentEffortDataUtils.validateSegmentEffortList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return AthleteDataUtils.ATHLETE_WITHOUT_FRIENDS;
	}

}
