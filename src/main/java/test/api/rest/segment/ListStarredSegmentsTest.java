package test.api.rest.segment;

import java.util.Arrays;

import javastrava.api.v3.model.StravaSegment;
import test.api.model.StravaSegmentTest;
import test.api.rest.APIListTest;
import test.issues.strava.Issue25;
import test.utils.TestUtils;

public class ListStarredSegmentsTest extends APIListTest<StravaSegment, Integer> {
	/**
	 *
	 */
	public ListStarredSegmentsTest() {
		this.listCallback = (api, id) -> api.listStarredSegments(id, null, null);
		this.pagingCallback = paging -> api().listStarredSegments(validId(), paging.getPage(), paging.getPageSize());
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
	protected void validate(final StravaSegment segment) {
		// This is a workaround for issue javastravav3api#25
		try {
			if (new Issue25().isIssue()) {
				return;
			}
		} catch (final Exception e) {
			// ignore
		}
		// End of workaround
		StravaSegmentTest.validateSegment(segment);
	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaSegment[] list) {
		StravaSegmentTest.validateList(Arrays.asList(list));

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
		return null;
	}

}
