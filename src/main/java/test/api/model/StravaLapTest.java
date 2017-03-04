package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaLap;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for StravaLap
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaLapTest extends BeanTest<StravaLap> {

	/**
	 * Validate a single lap
	 *
	 * @param lap
	 *            The lap to be validated
	 */
	public static void validateLap(final StravaLap lap) {
		validateLap(lap, lap.getId(), lap.getResourceState());
	}

	/**
	 * Validate a single lap
	 * 
	 * @param lap
	 *            The lap to be validated
	 * @param id
	 *            Expected id of the lap
	 * @param state
	 *            Expected resource state of the lap
	 */
	@SuppressWarnings("boxing")
	public static void validateLap(final StravaLap lap, final Long id, final StravaResourceState state) {
		assertNotNull(lap);
		assertEquals(id, lap.getId());
		assertEquals(state, lap.getResourceState());

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(lap.getActivity());
			StravaActivityTest.validate(lap.getActivity(), lap.getActivity().getId(), lap.getActivity().getResourceState());
			assertNotNull(lap.getAthlete());
			StravaAthleteTest.validateAthlete(lap.getAthlete(), lap.getAthlete().getId(), lap.getAthlete().getResourceState());
			if (lap.getAverageCadence() != null) {
				assertTrue(lap.getAverageCadence() >= 0);
			}
			if (lap.getAverageHeartrate() != null) {
				assertTrue(lap.getAverageHeartrate() >= 0);
				assertNotNull(lap.getMaxHeartrate());
				assertTrue(lap.getMaxHeartrate() >= lap.getAverageHeartrate());
			}
			if (lap.getMaxHeartrate() != null) {
				assertNotNull(lap.getAverageHeartrate());
			}
			assertNotNull(lap.getAverageSpeed());
			assertTrue(lap.getAverageSpeed() >= 0);
			if (lap.getAverageWatts() != null) {
				assertTrue(lap.getAverageWatts() >= 0);
				assertNotNull(lap.getDeviceWatts());
			}
			assertNotNull(lap.getDistance());
			assertTrue(lap.getDistance() >= 0);
			assertNotNull(lap.getElapsedTime());
			assertTrue(lap.getElapsedTime() >= 0);
			assertNotNull(lap.getEndIndex());
			assertNotNull(lap.getLapIndex());
			assertNotNull(lap.getMaxSpeed());
			assertTrue(lap.getMaxSpeed() >= lap.getAverageSpeed());
			assertNotNull(lap.getMovingTime());
			assertTrue(lap.getMovingTime() >= 0);
			assertNotNull(lap.getName());
			assertNotNull(lap.getStartDate());
			assertNotNull(lap.getStartDateLocal());
			assertNotNull(lap.getStartIndex());
			assertTrue(lap.getEndIndex() >= lap.getStartIndex());
			assertNotNull(lap.getTotalElevationGain());
			assertTrue(lap.getTotalElevationGain() >= 0);
			return;

		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(lap.getActivity());
			StravaActivityTest.validate(lap.getActivity(), lap.getActivity().getId(), lap.getActivity().getResourceState());
			assertNotNull(lap.getAthlete());
			StravaAthleteTest.validateAthlete(lap.getAthlete(), lap.getAthlete().getId(), lap.getAthlete().getResourceState());
			if (lap.getAverageCadence() != null) {
				assertTrue(lap.getAverageCadence() >= 0);
			}
			if (lap.getAverageHeartrate() != null) {
				assertTrue(lap.getAverageHeartrate() >= 0);
				assertNotNull(lap.getMaxHeartrate());
				assertTrue(lap.getMaxHeartrate() >= lap.getAverageHeartrate());
			}
			if (lap.getMaxHeartrate() != null) {
				assertNotNull(lap.getAverageHeartrate());
			}
			assertNotNull(lap.getAverageSpeed());
			assertTrue(lap.getAverageSpeed() >= 0);
			if (lap.getAverageWatts() != null) {
				assertTrue(lap.getAverageWatts() >= 0);
				assertNotNull(lap.getDeviceWatts());
			}
			assertNotNull(lap.getDistance());
			assertTrue(lap.getDistance() >= 0);
			assertNotNull(lap.getElapsedTime());
			assertTrue(lap.getElapsedTime() >= 0);
			assertNotNull(lap.getEndIndex());
			assertNotNull(lap.getLapIndex());
			assertNotNull(lap.getMaxSpeed());
			assertTrue(lap.getMaxSpeed() >= lap.getAverageSpeed());
			assertNotNull(lap.getMovingTime());
			assertTrue(lap.getMovingTime() >= 0);
			assertNotNull(lap.getName());
			assertNotNull(lap.getStartDate());
			assertNotNull(lap.getStartDateLocal());
			assertNotNull(lap.getStartIndex());
			assertTrue(lap.getEndIndex() >= lap.getStartIndex());
			assertNotNull(lap.getTotalElevationGain());
			assertTrue(lap.getTotalElevationGain() >= 0);
			return;

		}
		if (state == StravaResourceState.META) {
			assertNull(lap.getActivity());
			assertNull(lap.getAthlete());
			assertNull(lap.getAverageCadence());
			assertNull(lap.getAverageHeartrate());
			assertNull(lap.getMaxHeartrate());
			assertNull(lap.getAverageSpeed());
			assertNull(lap.getAverageWatts());
			assertNull(lap.getDeviceWatts());
			assertNull(lap.getDistance());
			assertNull(lap.getElapsedTime());
			assertNull(lap.getEndIndex());
			assertNull(lap.getLapIndex());
			assertNull(lap.getMaxSpeed());
			assertNull(lap.getMovingTime());
			assertNull(lap.getName());
			assertNull(lap.getStartDate());
			assertNull(lap.getStartDateLocal());
			assertNull(lap.getStartIndex());
			assertNull(lap.getTotalElevationGain());
			return;

		}
	}

	/**
	 * Validate a list of laps
	 *
	 * @param laps
	 *            The list of laps to be validated
	 */
	public static void validateList(final List<StravaLap> laps) {
		for (final StravaLap lap : laps) {
			validateLap(lap);
		}
	}

	@Override
	protected Class<StravaLap> getClassUnderTest() {
		return StravaLap.class;
	}
}
