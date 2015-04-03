package test.api.rest.segment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.issues.strava.Issue25;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListStarredSegmentsTest extends PagingArrayMethodTest<StravaSegment, Integer> {
	@Override
	protected ArrayCallback<StravaSegment> callback() {
		return (paging -> api().listStarredSegments(TestUtils.ATHLETE_AUTHENTICATED_ID, paging.getPage(), paging.getPageSize()));
	}

	@Test
	public void testListStarredSegments_authenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment[] segments = api().listStarredSegments(TestUtils.ATHLETE_AUTHENTICATED_ID, null, null);
			assertNotNull(segments);
		});
	}

	@Test
	public void testListStarredSegments_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listStarredSegments(TestUtils.ATHLETE_INVALID_ID, null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned starred segments for a non-existent athlete");
		});
	}

	@Test
	public void testListStarredSegments_otherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment[] segments = api().listStarredSegments(TestUtils.ATHLETE_VALID_ID, null, null);
			assertNotNull(segments);
		});
	}

	@Test
	public void testListStarredSegments_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment[] segments = api().listStarredSegments(TestUtils.ATHLETE_PRIVATE_ID, null, null);
			assertNotNull(segments);
			assertEquals(0, segments.length);
		});
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

	@Override
	protected void validate(final StravaSegment segment, final Integer id, final StravaResourceState state) {
		StravaSegmentTest.validateSegment(segment, id, state);

	}

}
