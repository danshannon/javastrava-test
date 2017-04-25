package test.api.athlete;

import java.util.Arrays;

import javastrava.api.API;
import javastrava.model.StravaSegmentEffort;
import test.api.APIPagingListTest;
import test.api.callback.APIListCallback;
import test.api.util.ArrayCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.SegmentEffortDataUtils;

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
		SegmentEffortDataUtils.validateSegmentEffort(effort);
	}

	@Override
	protected void validateArray(final StravaSegmentEffort[] list) {
		SegmentEffortDataUtils.validateSegmentEffortList(Arrays.asList(list));

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
