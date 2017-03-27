package test.api.rest.segment;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.rest.API;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue25;
import test.issues.strava.Issue71;
import test.issues.strava.Issue81;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#listAuthenticatedAthleteStarredSegments(Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteStarredSegmentsTest extends APIPagingListTest<StravaSegment, Integer> {
	@Override
	protected Integer invalidId() {
		return null;
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
	public void list_validParent() throws Exception {
		if (new Issue25().isIssue()) {
			return;
		}
		super.list_validParent();
	}

	@Override
	protected APIListCallback<StravaSegment, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteStarredSegments(null, null);
	}

	@Override
	protected ArrayCallback<StravaSegment> pagingCallback() {
		return paging -> api().listAuthenticatedAthleteStarredSegments(paging.getPage(), paging.getPageSize());
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
	public void testPageNumberAndSize() throws Exception {
		if (new Issue25().isIssue()) {
			return;
		}
		super.testPageNumberAndSize();
	}

	@Override
	public void testPageSize() throws Exception {
		if (new Issue25().isIssue()) {
			return;
		}
		super.testPageSize();
	}

	@Override
	protected void validate(final StravaSegment segment) {
		try {
			if (new Issue81().isIssue()) {
				return;
			}
		} catch (final Exception e) {
			// ignore
		}
		SegmentDataUtils.validateSegment(segment);

	}

	@Override
	protected void validateArray(final StravaSegment[] list) {
		SegmentDataUtils.validateSegmentList(Arrays.asList(list));

	}

	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
