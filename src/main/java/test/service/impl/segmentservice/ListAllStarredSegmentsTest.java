package test.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import test.api.model.StravaSegmentTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for listAllStarredSegments method
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class ListAllStarredSegmentsTest extends ListMethodTest<StravaSegment, Integer> {
	/**
	 * <p>
	 * List all starred segments for the authenticated athlete
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@Test
	public void listAllStarredSegments_authenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = lister().getList(TestUtils.strava(), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(segments);

			final List<StravaSegment> starredSegments = TestUtils.strava().listAllAuthenticatedAthleteStarredSegments();
			assertNotNull(starredSegments);
			assertEquals(starredSegments.size(), segments.size());
		});
	}

	@Override
	protected ListCallback<StravaSegment, Integer> lister() {
		return ((strava, id) -> strava.listAllStarredSegments(id));
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
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected Integer idInvalid() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected void validate(StravaSegment object) {
		StravaSegmentTest.validateSegment(object);

	}

}
