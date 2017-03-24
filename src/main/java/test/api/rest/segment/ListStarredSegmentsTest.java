package test.api.rest.segment;

import java.util.Arrays;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.rest.API;
import test.api.model.StravaSegmentTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue25;
import test.service.standardtests.data.AthleteDataUtils;

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
	protected APIListCallback<StravaSegment, Integer> listCallback() {
		return (api, id) -> api.listStarredSegments(id, null, null);
	}

	/**
	 * @see test.api.rest.APIPagingListTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaSegment> pagingCallback() {
		return paging -> api().listStarredSegments(validId(), paging.getPage(), paging.getPageSize());
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
	protected void validate(final StravaSegment segment) {
		try {
			if (new Issue25().isIssue()) {
				return;
			}
		} catch (final Exception e) {
			// ignore
		}

		StravaSegmentTest.validateSegment(segment);
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

		StravaSegmentTest.validateList(Arrays.asList(list));

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
		return null;
	}

}
