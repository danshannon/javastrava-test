package test.api.rest.athlete;

import java.util.Arrays;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.rest.API;
import test.api.model.StravaSegmentEffortTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Specific tests for {@link API#listAthleteKOMs(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAthleteKOMsTest extends APIPagingListTest<StravaSegmentEffort, Integer> {
	@Override
	protected Integer invalidId() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected APIListCallback<StravaSegmentEffort, Integer> listCallback() {
		return (api, id) -> api.listAthleteKOMs(id, null, null);
	}

	@Override
	protected ArrayCallback<StravaSegmentEffort> pagingCallback() {
		return paging -> api().listAthleteKOMs(validId(), paging.getPage(), paging.getPageSize());
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		StravaSegmentEffortTest.validateSegmentEffort(effort);
	}

	@Override
	protected void validateArray(final StravaSegmentEffort[] list) {
		StravaSegmentEffortTest.validateList(Arrays.asList(list));

	}

	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected Integer validIdNoChildren() {
		return AthleteDataUtils.ATHLETE_WITHOUT_FRIENDS;
	}

}
