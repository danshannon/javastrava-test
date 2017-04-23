package test.service.impl.segmentservice;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for listAllAuthenticatedAthleteStarredSegments methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllAuthenticatedAthleteStarredSegmentsTest extends ListMethodTest<StravaSegment, Integer> {
	@Override
	protected Class<StravaSegment> classUnderTest() {
		return StravaSegment.class;
	}

	@Override
	protected Integer idInvalid() {
		return null;
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
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaSegment, Integer> lister() {
		return ((strava, id) -> strava.listAllAuthenticatedAthleteStarredSegments());
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

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = lister().getList(TestUtils.stravaWithViewPrivate(), null);
			boolean pass = false;
			for (final StravaSegment segment : segments) {
				if (segment.getPrivateSegment().equals(Boolean.TRUE)) {
					pass = true;
				}
			}
			assertTrue(pass);
		});
	}

	@Override
	protected void validate(StravaSegment segment) {
		SegmentDataUtils.validateSegment(segment);
	}
}
