package test.api.rest.segment;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import test.api.model.StravaSegmentTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue25;
import test.issues.strava.Issue71;
import test.issues.strava.Issue81;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;

public class ListAuthenticatedAthleteStarredSegmentsTest extends APIPagingListTest<StravaSegment, Integer> {
	/**
	 * @see test.api.rest.APIPagingListTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaSegment> pagingCallback() {
		return paging -> api().listAuthenticatedAthleteStarredSegments(paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaSegment, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteStarredSegments(null, null);
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return null;
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
	public void list_privateWithoutViewPrivate() throws Exception {
		final Issue71 issue71 = new Issue71();
		if (issue71.isIssue()) {
			return;
		}
		// End of workaround

		final StravaSegment[] segments = api().listAuthenticatedAthleteStarredSegments(null, null);
		for (final StravaSegment segment : segments) {
			if ((segment.getPrivateSegment() != null) && segment.getPrivateSegment().equals(Boolean.TRUE)) {
				fail("Returned at least one private starred segment"); //$NON-NLS-1$
			}
		}
	}

	@Override
	@Test
	public void list_private() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment[] segments = apiWithViewPrivate().listAuthenticatedAthleteStarredSegments(null, null);
			boolean pass = false;
			for (final StravaSegment segment : segments) {
				if (segment.getPrivateSegment().booleanValue()) {
					pass = true;
				}
			}
			assertTrue(pass);
		});
	}

	@Override
	protected void validate(final StravaSegment segment) {
		// TODO This is a workaround for issue javastravav3api#81
		try {
			if (new Issue81().isIssue()) {
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
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#list_validParent()
	 */
	@Override
	public void list_validParent() throws Exception {
		if (new Issue25().isIssue()) {
			return;
		}
		super.list_validParent();
	}

	/**
	 * @see test.api.rest.APIPagingListTest#testPageNumberAndSize()
	 */
	@Override
	public void testPageNumberAndSize() throws Exception {
		if (new Issue25().isIssue()) {
			return;
		}
		super.testPageNumberAndSize();
	}

	/**
	 * @see test.api.rest.APIPagingListTest#testPageSize()
	 */
	@Override
	public void testPageSize() throws Exception {
		if (new Issue25().isIssue()) {
			return;
		}
		super.testPageSize();
	}

}
