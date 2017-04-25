package test.api.segment;

import java.util.Arrays;

import javastrava.api.API;
import javastrava.model.StravaSegment;
import test.api.APIPagingListTest;
import test.api.callback.APIListCallback;
import test.api.util.ArrayCallback;
import test.issues.strava.Issue25;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.SegmentDataUtils;

/**
 * <p>
 * Specific config and tests for {@link API#listStarredSegments(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListStarredSegmentsTest extends APIPagingListTest<StravaSegment, Integer> {
	/**
	 * @see test.api.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	/**
	 * @see test.api.APIListTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaSegment, Integer> listCallback() {
		return (api, id) -> api.listStarredSegments(id, null, null);
	}

	/**
	 * @see test.api.APIPagingListTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaSegment> pagingCallback() {
		return paging -> api().listStarredSegments(validId(), paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(final StravaSegment segment) {
		try {
			if (new Issue25().isIssue()) {
				return;
			}
		} catch (final Exception e) {
			// ignore
		}

		SegmentDataUtils.validateSegment(segment);
	}

	@Override
	protected void validateArray(final StravaSegment[] list) {
		// This is a workaround for issue javastravav3api#25
		try {
			if (new Issue25().isIssue()) {
				return;
			}
		} catch (final Exception e) {
			// Ignore
		}

		SegmentDataUtils.validateSegmentList(Arrays.asList(list));

	}

	/**
	 * @see test.api.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	/**
	 * @see test.api.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
