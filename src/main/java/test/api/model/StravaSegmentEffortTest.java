package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaAchievement;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSegmentEffort}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentEffortTest extends BeanTest<StravaSegmentEffort> {

	/**
	 * Validate a list of efforts
	 * 
	 * @param list
	 *            The list to be validated
	 */
	public static void validateList(final List<StravaSegmentEffort> list) {
		for (final StravaSegmentEffort effort : list) {
			validateSegmentEffort(effort);
		}

	}

	/**
	 * Validate the structure and content of an effort
	 * 
	 * @param effort
	 *            The effort to be validated
	 */
	public static void validateSegmentEffort(final StravaSegmentEffort effort) {
		validateSegmentEffort(effort, effort.getId(), effort.getResourceState());
	}

	/**
	 * Validate the structure and content of an effort
	 * 
	 * @param effort
	 *            The effort to be validated
	 * @param id
	 *            The expected identifier of the effort
	 * @param state
	 *            The expected resource state of the effort
	 */
	@SuppressWarnings("boxing")
	public static void validateSegmentEffort(final StravaSegmentEffort effort, final Long id, final StravaResourceState state) {
		assertNotNull(effort);
		assertEquals(id, effort.getId());
		assertEquals(state, effort.getResourceState());

		if ((state == StravaResourceState.DETAILED) || (state == StravaResourceState.SUMMARY)) {
			if (effort.getActivity() != null) {
				StravaActivityTest.validate(effort.getActivity(), effort.getActivity().getId(),
						effort.getActivity().getResourceState());
			}
			if (effort.getAthlete() != null) {
				StravaAthleteTest.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(),
						effort.getAthlete().getResourceState());
			}
			if ((effort.getActivity() != null) && (effort.getActivity().getAthlete() != null)) {
				assertEquals(effort.getActivity().getAthlete().getId(), effort.getAthlete().getId());
			}
			// Only returned for rides, and then only if it was measured
			if (effort.getActivity() != null) {
				if (effort.getActivity().getType() == StravaActivityType.RIDE) {
					if (effort.getAverageCadence() != null) {
						assertTrue(effort.getAverageCadence() >= 0);
					}
					if (effort.getAverageWatts() != null) {
						assertTrue(effort.getAverageWatts() >= 0);
					}
				} else {
					// If we can't tell what sort of activity it was, then can't
					// tell if average cadence/watts can be set or not
					if (effort.getActivity().getResourceState() != StravaResourceState.META) {
						assertNull(effort.getAverageCadence());
						assertNull(effort.getAverageWatts());
					}
				}
			}
			// If returned then there should be a max heartrate too
			if (effort.getAverageHeartrate() != null) {
				assertTrue(effort.getAverageHeartrate() >= 0);
				assertTrue(effort.getMaxHeartrate() >= 0);
				assertTrue(effort.getMaxHeartrate() >= effort.getAverageHeartrate());
			}
			// If there's a max heartrate, then there should be an average
			if (effort.getMaxHeartrate() != null) {
				assertNotNull(effort.getAverageHeartrate());
			}
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			// Only returned if in the top 10 at the time
			if (effort.getKomRank() != null) {
				assertTrue(effort.getKomRank() <= 10);
				assertTrue(effort.getKomRank() >= 1);
			}
			// Only returned if it's one of the top 3 efforts for the athlete
			if (effort.getPrRank() != null) {
				assertTrue(effort.getPrRank() > 0);
				assertTrue(effort.getPrRank() < 4);
			}
			if (effort.getEndIndex() != null) {
				assertNotNull(effort.getStartIndex());
				assertTrue(effort.getEndIndex() > effort.getStartIndex());
			}
			if (effort.getStartIndex() != null) {
				assertNotNull(effort.getEndIndex());
			}
			if (effort.getSegment() != null) {
				StravaSegmentTest.validateSegment(effort.getSegment());
			}
			// Optional (only returned with activities)
			// assertNotNull(effort.getHidden());

			// Only returned for starred segments
			// assertNotNull(effort.getIsKom());

			assertNotNull(effort.getStartDate());
			assertNotNull(effort.getStartDateLocal());

			// Not always returned
			if (effort.getAthleteSegmentStats() != null) {
				StravaAthleteSegmentStatsTest.validate(effort.getAthleteSegmentStats());
			}
			if (effort.getAchievements() != null) {
				for (final StravaAchievement achievement : effort.getAchievements()) {
					StravaAchievementTest.validate(achievement);
				}
			}

		}

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(effort.getEndIndex());
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			assertNotNull(effort.getSegment());
			assertNotNull(effort.getStartIndex());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			// Optional (not returned with athlete_pr_effort in starred
			// segments)
			// assertNotNull(effort.getEndIndex());

			// Optional (not returned with athlete_pr_effort in starred
			// segments)
			// assertNotNull(effort.getMovingTime());

			// Optional (not returned with athlete_pr_effort in starred
			// segments)
			// assertNotNull(effort.getName());

			// Optional (not returned with athlete_pr_effort in starred
			// segments)
			// assertNotNull(effort.getSegment());

			// Optional (not returned with athlete_pr_effort in starred
			// segments)
			// assertNotNull(effort.getStartIndex());
			return;
		}
		if ((state == StravaResourceState.META) || (state == StravaResourceState.PRIVATE)) {
			assertNotNull(effort.getId());
			assertNull(effort.getActivity());
			assertNull(effort.getAthlete());
			assertNull(effort.getAverageCadence());
			assertNull(effort.getAverageHeartrate());
			assertNull(effort.getAverageWatts());
			assertNull(effort.getDistance());
			assertNull(effort.getElapsedTime());
			assertNull(effort.getEndIndex());
			assertNull(effort.getHidden());
			assertNull(effort.getIsKom());
			assertNull(effort.getKomRank());
			assertNull(effort.getMaxHeartrate());
			assertNull(effort.getMovingTime());
			assertNull(effort.getName());
			assertNull(effort.getPrRank());
			assertNull(effort.getSegment());
			assertNull(effort.getStartDate());
			assertNull(effort.getStartDateLocal());
			assertNull(effort.getStartIndex());
			return;
		}
		fail("Unexpected state for segment effort " + state + " " + effort); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected Class<StravaSegmentEffort> getClassUnderTest() {
		return StravaSegmentEffort.class;
	}
}
