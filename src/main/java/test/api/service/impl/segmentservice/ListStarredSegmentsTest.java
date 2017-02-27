package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.service.Strava;
import test.api.model.StravaSegmentTest;
import test.api.service.standardtests.PagingListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.api.service.standardtests.callbacks.PagingListCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#listStarredSegments(Integer)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListStarredSegmentsTest extends PagingListMethodTest<StravaSegment, Integer> {
	@Override
	protected PagingListCallback<StravaSegment, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listStarredSegments(id, paging));
	}

	@Override
	protected ListCallback<StravaSegment, Integer> lister() {
		return ((strava, id) -> strava.listStarredSegments(id));
	}

	/**
	 * <p>
	 * Test for authenticated user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testListStarredSegments_authenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = lister().getList(TestUtils.strava(), TestUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(segments);
		});
	}

	@Override
	protected void validate(final StravaSegment segment) {
		StravaSegmentTest.validateSegment(segment);
	}

	@Override
	protected Integer idPrivate() {
		return TestUtils.SEGMENT_PRIVATE_ID;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	@Override
	protected Integer idValidWithEntries() {
		return TestUtils.SEGMENT_VALID_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected Integer idInvalid() {
		return TestUtils.SEGMENT_INVALID_ID;
	}
}
