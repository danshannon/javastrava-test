package test.service.impl.athleteservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.model.StravaSegment;
import javastrava.model.StravaSegmentEffort;
import javastrava.model.StravaSegmentLeaderboard;
import javastrava.model.StravaSegmentLeaderboardEntry;
import javastrava.service.Strava;
import javastrava.service.exception.UnauthorizedException;
import test.issues.strava.Issue32;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests and configuration for {@link Strava#listAllAthleteKOMs(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllAthleteKOMsTest extends ListMethodTest<StravaSegmentEffort, Integer> {
	@Override
	protected Class<StravaSegmentEffort> classUnderTest() {
		return StravaSegmentEffort.class;
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

	/**
	 * Check if the athlete is the KOM for the given segment
	 *
	 * @param segment
	 *            Segment to check
	 * @param athleteId
	 *            Athlete id
	 * @return <code>true</code> if the athlete is the KOM
	 */
	@SuppressWarnings({ "static-method", "boxing" })
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

	@Override
	protected ListCallback<StravaSegmentEffort, Integer> lister() {
		return ((strava, id) -> strava.listAllAthleteKOMs(id));
	}

	/**
	 * Test listing KOM's for the authenticated athlete
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@Test
	public void testListAllAthleteKOMs_authenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllAthleteKOMs(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				SegmentEffortDataUtils.validateSegmentEffort(effort);
				assertTrue("Segment " + effort.getSegment().getId() + " athlete " + AthleteDataUtils.ATHLETE_AUTHENTICATED_ID + " is not the KOM!", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						isKom(effort.getSegment(), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID));
			}
		});
	}

	/**
	 * Test listing KOM's for the authenticated athlete, using a token without view_private scope, doesn't return any segments that are flagged as private
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAthleteKOMs_authenticatedAthletePrivateActivitiesWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = TestUtils.strava().listAllAthleteKOMs(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort kom : koms) {
				try {
					TestUtils.strava().getActivity(kom.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM for a private segment!"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * Test listing all KOM's for an athlete other than the authenticated athlete
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@Test
	public void testListAllAthleteKOMs_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllAthleteKOMs(AthleteDataUtils.ATHLETE_VALID_ID);
			assertNotNull(efforts);

			// workaround for issue javastrava-api #32 - see
			// https://github.com/danshannon/javastravav3api/issues/32
			if (new Issue32().isIssue()) {
				return;
			}
			// End of workaround

			for (final StravaSegmentEffort effort : efforts) {
				SegmentEffortDataUtils.validateSegmentEffort(effort);
				assertTrue("Segment " + effort.getSegment().getId() + " athlete " + AthleteDataUtils.ATHLETE_VALID_ID + " is not the KOM!", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						isKom(effort.getSegment(), AthleteDataUtils.ATHLETE_VALID_ID));
			}
		});
	}

	/**
	 * Test that listing KOM's for another user doesn't return KOM's achieved on a private activity
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAthleteKOMs_otherAthletePrivateActivities() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllAthleteKOMs(AthleteDataUtils.ATHLETE_VALID_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getActivity(effort.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * Test that listing KOM's for another athlete doesn't return KOM's on a private segment
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAthleteKOMs_otherAthletePrivateSegments() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllAthleteKOMs(AthleteDataUtils.ATHLETE_VALID_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getSegment(effort.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!"); //$NON-NLS-1$
				}
			}
		});
	}

	@Override
	protected void validate(StravaSegmentEffort object) {
		SegmentEffortDataUtils.validateSegmentEffort(object);
	}

}
