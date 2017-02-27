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
 * @author Dan Shannon
 *
 */
public class StravaLapTest extends BeanTest<StravaLap> {

	public static void validateLap(final StravaLap lap) {
		validateLap(lap, lap.getId(), lap.getResourceState());
	}

	public static void validateLap(final StravaLap lap, final Long id, final StravaResourceState state) {
		assertNotNull(lap);
		assertEquals(id, lap.getId());
		assertEquals(state, lap.getResourceState());

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(lap.getActivity());
			StravaActivityTest.validateActivity(lap.getActivity(), lap.getActivity().getId(), lap.getActivity().getResourceState());
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
			StravaActivityTest.validateActivity(lap.getActivity(), lap.getActivity().getId(), lap.getActivity().getResourceState());
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
