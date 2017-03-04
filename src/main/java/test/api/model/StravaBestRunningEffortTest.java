package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javastrava.api.v3.model.StravaBestRunningEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for StravaBestRunningEffort
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaBestRunningEffortTest extends BeanTest<StravaBestRunningEffort> {

	/**
	 * @param effort
	 *            Effort to validate
	 * @param id
	 *            Effort identifier
	 * @param state
	 *            Resource state
	 */
	public static void validateBestEffort(final StravaBestRunningEffort effort, final Integer id, final StravaResourceState state) {
		assertNotNull(effort);
		assertEquals(id, effort.getId());
		assertEquals(state, effort.getResourceState());

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(effort.getActivity());
			// NB Don't validate the activity - that way lies 1 Infinite Loop
			assertNotNull(effort.getAthlete());
			StravaAthleteTest.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(),
					effort.getAthlete().getResourceState());
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			// Optional assertNotNull(effort.getKomRank());
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			// Optional assertNotNull(effort.getPrRank());
			// Optional assertNotNull(effort.getSegment());
			if (effort.getSegment() != null) {
				StravaSegmentTest.validateSegment(effort.getSegment(), effort.getSegment().getId(),
						effort.getSegment().getResourceState());
			}
			assertNotNull(effort.getStartDate());
			assertNotNull(effort.getStartDateLocal());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(effort.getActivity());
			// NB Don't validate the activity - that way lies 1 Infinite Loop
			assertNotNull(effort.getAthlete());
			StravaAthleteTest.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(),
					effort.getAthlete().getResourceState());
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			// Optional assertNotNull(effort.getKomRank());
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			// Optional assertNotNull(effort.getPrRank());
			// Optional assertNotNull(effort.getSegment());
			if (effort.getSegment() != null) {
				StravaSegmentTest.validateSegment(effort.getSegment(), effort.getSegment().getId(),
						effort.getSegment().getResourceState());
			}
			assertNotNull(effort.getStartDate());
			assertNotNull(effort.getStartDateLocal());
			return;
		}
		if (state == StravaResourceState.META) {
			assertNull(effort.getActivity());
			// NB Don't validate the activity - that way lies 1 Infinite Loop
			assertNull(effort.getAthlete());
			assertNull(effort.getDistance());
			assertNull(effort.getElapsedTime());
			assertNull(effort.getKomRank());
			assertNull(effort.getMovingTime());
			assertNull(effort.getName());
			assertNull(effort.getPrRank());
			assertNull(effort.getSegment());
			assertNull(effort.getStartDate());
			assertNull(effort.getStartDateLocal());
			return;
		}
		fail("Unexpected state " + state + " for best effort " + effort); //$NON-NLS-1$ //$NON-NLS-2$

	}

	@Override
	protected Class<StravaBestRunningEffort> getClassUnderTest() {
		return StravaBestRunningEffort.class;
	}

}
