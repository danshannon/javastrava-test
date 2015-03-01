package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaBestRunningEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * @author dshannon
 *
 */
public class StravaBestRunningEffortTest extends BeanTest<StravaBestRunningEffort> {

	@Override
	protected Class<StravaBestRunningEffort> getClassUnderTest() {
		return StravaBestRunningEffort.class;
	}

	/**
	 * @param effort
	 * @param id
	 * @param resourceState
	 */
	public static void validateBestEffort(StravaBestRunningEffort effort, Integer id, StravaResourceState state) {
		assertNotNull(effort);
		assertEquals(id, effort.getId());
		assertEquals(state, effort.getResourceState());
		
		if (state == StravaResourceState.DETAILED) {
			assertNotNull(effort.getActivity());
			// NB Don't validate the activity - that way lies 1 Infinite Loop
			assertNotNull(effort.getAthlete());
			StravaAthleteTest.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(), effort.getAthlete().getResourceState());
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			assertNotNull(effort.getKomRank());
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			assertNotNull(effort.getPrRank());
			assertNotNull(effort.getSegment());
			StravaSegmentTest.validateSegment(effort.getSegment(), effort.getSegment().getId(), effort.getSegment().getResourceState());
			assertNotNull(effort.getStartDate());
			assertNotNull(effort.getStartDateLocal());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(effort.getActivity());
			// NB Don't validate the activity - that way lies 1 Infinite Loop
			assertNotNull(effort.getAthlete());
			StravaAthleteTest.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(), effort.getAthlete().getResourceState());
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			assertNotNull(effort.getKomRank());
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			assertNotNull(effort.getPrRank());
			assertNotNull(effort.getSegment());
			StravaSegmentTest.validateSegment(effort.getSegment(), effort.getSegment().getId(), effort.getSegment().getResourceState());
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
		fail("Unexpected state " + state + " for best effort " + effort);
	
	}

}
