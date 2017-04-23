package test.service.impl.segmentservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.service.Strava;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
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
	protected Class<StravaSegment> classUnderTest() {
		return StravaSegment.class;
	}

	@Override
	protected Integer idInvalid() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected Integer idPrivate() {
		return AthleteDataUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaSegment, Integer> lister() {
		return ((strava, id) -> strava.listStarredSegments(id));
	}

	@Override
	protected PagingListCallback<StravaSegment, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listStarredSegments(id, paging));
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
			final List<StravaSegment> segments = lister().getList(TestUtils.strava(), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(segments);
		});
	}

	@Override
	protected void validate(final StravaSegment segment) {
		SegmentDataUtils.validateSegment(segment);
	}
}
