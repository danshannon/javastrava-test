package test.service.impl.athleteservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaSegmentEffortTest;
import test.issues.strava.Issue32;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAllAthleteKOMsTest extends ListMethodTest<StravaSegmentEffort, Integer> {
	private boolean isKom(final StravaSegment segment, final Integer athleteId) {
		final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(segment.getId());
		boolean isKom = false;
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			if (entry.getAthleteId().equals(athleteId) && entry.getRank().equals(1)) {
				isKom = true;
			}
		}
		return isKom;
	}

	@Test
	public void testListAllAthleteKOMs_authenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				StravaSegmentEffortTest.validateSegmentEffort(effort);
				assertTrue("Segment " + effort.getSegment().getId() + " athlete " + TestUtils.ATHLETE_AUTHENTICATED_ID
						+ " is not the KOM!", isKom(effort.getSegment(), TestUtils.ATHLETE_AUTHENTICATED_ID));
			}
		});
	}

	@Test
	public void testListAllAthleteKOMs_authenticatedAthletePrivateActivitiesWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = TestUtils.strava().listAllAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort kom : koms) {
				try {
					TestUtils.strava().getActivity(kom.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM for a private segment!");
				}
			}
		});
	}

	@Test
	public void testListAllAthleteKOMs_authenticatedAthletePrivateActivitiesWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = TestUtils.stravaWithViewPrivate()
					.listAllAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort kom : koms) {
				try {
					TestUtils.strava().getActivity(kom.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM for a private segment!");
				}
			}
		});
	}

	@Test
	public void testListAllAthleteKOMs_authenticatedAthletePrivateSegmentsWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = TestUtils.strava().listAllAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort kom : koms) {
				try {
					TestUtils.strava().getSegment(kom.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM for a private segment!");
				}
			}
		});
	}

	@Test
	public void testListAllAthleteKOMs_authenticatedAthletePrivateSegmentsWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = TestUtils.stravaWithViewPrivate()
					.listAllAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort kom : koms) {
				try {
					TestUtils.strava().getSegment(kom.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM for a private segment!");
				}
			}
		});
	}

	@Test
	public void testListAllAthleteKOMs_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllAthleteKOMs(TestUtils.ATHLETE_VALID_ID);
			assertNotNull(efforts);
			// TODO workaround for issue javastrava-api #32 - see
			// https://github.com/danshannon/javastravav3api/issues/32
			if (new Issue32().isIssue()) {
				return;
			}
			// End of workaround

			for (final StravaSegmentEffort effort : efforts) {
				StravaSegmentEffortTest.validateSegmentEffort(effort);
				assertTrue("Segment " + effort.getSegment().getId() + " athlete " + TestUtils.ATHLETE_VALID_ID + " is not the KOM!",
						isKom(effort.getSegment(), TestUtils.ATHLETE_VALID_ID));
			}
		});
	}

	@Test
	public void testListAllAthleteKOMs_otherAthletePrivateActivities() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllAthleteKOMs(TestUtils.ATHLETE_VALID_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getActivity(effort.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!");
				}
			}
		});
	}

	@Test
	public void testListAllAthleteKOMs_otherAthletePrivateSegments() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllAthleteKOMs(TestUtils.ATHLETE_VALID_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getSegment(effort.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!");
				}
			}
		});
	}

	@Override
	protected ListCallback<StravaSegmentEffort, Integer> lister() {
		return ((strava, id) -> strava.listAllAthleteKOMs(id));
	}

	@Override
	protected Integer idPrivate() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return null;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected Integer idInvalid() {
		return TestUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected void validate(StravaSegmentEffort object) {
		StravaSegmentEffortTest.validateSegmentEffort(object);
	}

}
