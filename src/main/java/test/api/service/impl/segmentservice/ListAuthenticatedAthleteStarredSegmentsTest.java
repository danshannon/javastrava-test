package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.Strava;
import test.api.model.StravaSegmentTest;
import test.api.service.standardtests.PagingListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.api.service.standardtests.callbacks.PagingListCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#listAuthenticatedAthleteStarredSegments()} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteStarredSegmentsTest extends PagingListMethodTest<StravaSegment, Integer> {
	@Override
	protected PagingListCallback<StravaSegment, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listAuthenticatedAthleteStarredSegments(paging));
	}

	@Test
	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = lister().getList(TestUtils.strava(), null);
			for (final StravaSegment segment : segments) {
				if ((segment.getPrivateSegment() != null) && segment.getPrivateSegment().equals(Boolean.TRUE)) {
					fail("Returned at least one private starred segment"); //$NON-NLS-1$
				}
			}
		});
	}

	@Test
	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = lister().getList(TestUtils.stravaWithViewPrivate(), null);
			boolean pass = false;
			for (final StravaSegment segment : segments) {
				if (segment.getResourceState() == StravaResourceState.PRIVATE) {
					pass = true;
				}
			}
			assertTrue(pass);
		});
	}

	@Override
	protected void validate(final StravaSegment segment) {
		StravaSegmentTest.validateSegment(segment);

	}

	@Override
	protected ListCallback<StravaSegment, Integer> lister() {
		return ((strava, id) -> strava.listAuthenticatedAthleteStarredSegments());
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected Integer idInvalid() {
		return null;
	}

}
