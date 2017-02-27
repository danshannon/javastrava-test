package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaBestRunningEffort;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSplit;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaWorkoutType;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityTest extends BeanTest<StravaActivity> {

	public static void validateActivity(final StravaActivity activity) {
		validateActivity(activity, activity.getId(), activity.getResourceState());
	}

	public static void validateActivity(final StravaActivity activity, final Long id, final StravaResourceState state) {
		assertNotNull(activity);
		assertEquals(id, activity.getId());
		assertEquals(state, activity.getResourceState());

		if (state == StravaResourceState.UPDATING) {
			assertNotNull(activity.getId());
			return;
		}

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(activity.getAchievementCount());
			assertNotNull(activity.getAthlete());
			assertTrue((activity.getAthlete().getResourceState() == StravaResourceState.META)
					|| (activity.getAthlete().getResourceState() == StravaResourceState.SUMMARY));
			StravaAthleteTest.validateAthlete(activity.getAthlete(), activity.getAthlete().getId(),
					activity.getAthlete().getResourceState());
			assertNotNull(activity.getName());
			assertNotNull(activity.getDistance());
			assertTrue(activity.getDistance() >= 0);
			assertNotNull(activity.getAthleteCount());
			if (activity.getType() == StravaActivityType.RIDE) {
				if (activity.getAverageCadence() != null) {
					assertTrue(activity.getAverageCadence() >= 0);
				}
				if (activity.getAverageWatts() != null) {
					assertTrue(activity.getAverageWatts() >= 0);
				}
				assertNotNull(activity.getDeviceWatts());
				if (activity.getDeviceWatts()) {
					assertNotNull(activity.getWeightedAverageWatts());
				}
			} else {
				assertNull(activity.getAverageCadence());
				assertNull(activity.getAverageWatts());
				assertNull(activity.getDeviceWatts());
				assertNull(activity.getWeightedAverageWatts());
			}
			if (activity.getAverageHeartrate() != null) {
				assertTrue(activity.getAverageHeartrate() >= 0);
				assertNotNull(activity.getMaxHeartrate());
				assertTrue(activity.getMaxHeartrate() >= activity.getAverageHeartrate());
			}
			if (activity.getMaxHeartrate() != null) {
				assertNotNull(activity.getAverageHeartrate());
			}

			assertNotNull(activity.getAverageSpeed());
			// Optional assertNotNull(activity.getAverageTemp());
			if (activity.getType() == StravaActivityType.RUN) {
				assertNotNull(activity.getBestEfforts());
				for (final StravaBestRunningEffort effort : activity.getBestEfforts()) {
					StravaBestRunningEffortTest.validateBestEffort(effort, effort.getId(), effort.getResourceState());
				}
				if (!activity.getManual()) {
					assertNotNull(activity.getSplitsMetric());
					for (final StravaSplit split : activity.getSplitsMetric()) {
						StravaSplitTest.validateSplit(split);
					}
					assertNotNull(activity.getSplitsStandard());
					for (final StravaSplit split : activity.getSplitsStandard()) {
						StravaSplitTest.validateSplit(split);
					}
				}
			}
			assertNotNull(activity.getCalories());
			assertNotNull(activity.getCommentCount());
			assertNotNull(activity.getCommute());
			// OPTIONAL assertNotNull(activity.getDescription());
			assertNotNull(activity.getElapsedTime());
			// Optional (because sometimes GPS doesn't work properly)
			// assertNull(activity.getEndLatlng());
			// Optional assertNotNull(activity.getExternalId());
			assertNotNull(activity.getFlagged());
			if (activity.getGear() != null) {
				StravaGearTest.validateGear(activity.getGear(), activity.getGearId(), activity.getGear().getResourceState());
			}
			assertNotNull(activity.getHasKudoed());
			if ((activity.getType() == StravaActivityType.RIDE) && !activity.getManual() && !activity.getTrainer()
					&& (activity.getCalories() != null)) {
				assertNotNull(activity.getKilojoules());
			}
			assertNotNull(activity.getKudosCount());
			// Optional assertNotNull(activity.getLocationCity());
			// Optional assertNotNull(activity.getLocationCountry());
			// Optional assertNotNull(activity.getLocationState());
			assertNotNull(activity.getManual());
			assertNotNull(activity.getMap());
			if (!activity.getManual() && !activity.getTrainer()) {
				StravaMapTest.validateMap(activity.getMap(), activity.getMap().getId(), activity.getMap().getResourceState(),
						activity);
			}
			assertNotNull(activity.getMaxSpeed());
			assertTrue(activity.getMaxSpeed() >= 0);
			assertNotNull(activity.getMovingTime());
			assertNotNull(activity.getName());
			assertNotNull(activity.getPhotoCount());
			assertNotNull(activity.getPrivateActivity());
			assertNotNull(activity.getSegmentEfforts());
			for (final StravaSegmentEffort effort : activity.getSegmentEfforts()) {
				StravaSegmentEffortTest.validateSegmentEffort(effort, effort.getId(), effort.getResourceState());
			}
			assertNotNull(activity.getStartDate());
			assertNotNull(activity.getStartDateLocal());
			// Optional (because sometimes GPS doesn't work properly)
			// assertNull(activity.getStartLatlng());
			assertNotNull(activity.getTimezone());
			assertNotNull(activity.getTotalElevationGain());
			assertNotNull(activity.getTrainer());
			assertNotNull(activity.getType());
			assertFalse(activity.getType() == StravaActivityType.UNKNOWN);
			// if (activity.getType() != StravaActivityType.RUN) {
			// assertNull(activity.getWorkoutType());
			// }
			if (activity.getWorkoutType() != null) {
				assertFalse(activity.getWorkoutType() == StravaWorkoutType.UNKNOWN);
			}
			if (activity.getPhotos() != null) {
				StravaActivityPhotosTest.validate(activity.getPhotos());
			}
			if (activity.getVideo() != null) {
				StravaVideoTest.validate(activity.getVideo());
			}
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(activity.getAchievementCount());
			assertNotNull(activity.getAthlete());
			assertTrue((activity.getAthlete().getResourceState() == StravaResourceState.META)
					|| (activity.getAthlete().getResourceState() == StravaResourceState.SUMMARY));
			StravaAthleteTest.validateAthlete(activity.getAthlete(), activity.getAthlete().getId(),
					activity.getAthlete().getResourceState());
			assertNotNull(activity.getName());
			assertNotNull(activity.getDistance());
			assertTrue(activity.getDistance() >= 0);
			assertNotNull(activity.getAthleteCount());
			if (activity.getType() == StravaActivityType.RIDE) {
				if (activity.getAverageCadence() != null) {
					assertTrue(activity.getAverageCadence() >= 0);
				}
				if (activity.getAverageWatts() != null) {
					assertTrue(activity.getAverageWatts() >= 0);
				}
				assertNotNull(activity.getDeviceWatts());
				if (activity.getDeviceWatts()) {
					assertNotNull(activity.getWeightedAverageWatts());
				}
			}
			if (activity.getAverageHeartrate() != null) {
				assertTrue(activity.getAverageHeartrate() >= 0);
				assertNotNull(activity.getMaxHeartrate());
				assertTrue(activity.getMaxHeartrate() >= activity.getAverageHeartrate());
			}
			if (activity.getMaxHeartrate() != null) {
				assertNotNull(activity.getAverageHeartrate());
			}
			assertNotNull(activity.getAverageSpeed());
			// Optional assertNotNull(activity.getAverageTemp());
			assertNull(activity.getBestEfforts());
			assertNull(activity.getCalories());
			assertNotNull(activity.getCommentCount());
			assertNotNull(activity.getCommute());
			assertNull(activity.getDescription());
			assertNotNull(activity.getElapsedTime());
			// Optional (because sometimes GPS doesn't work properly)
			// assertNull(activity.getEndLatlng());
			// Optional assertNotNull(activity.getExternalId());
			assertNotNull(activity.getFlagged());
			assertNull(activity.getGear());
			// Optional assertNotNull(activity.getGearId());
			assertNotNull(activity.getHasKudoed());
			if ((activity.getType() == StravaActivityType.RIDE) && !activity.getManual() && !activity.getTrainer()
					&& (activity.getCalories() != null)) {
				assertNotNull(activity.getKilojoules());
			}
			assertNotNull(activity.getKudosCount());
			// Optional assertNotNull(activity.getLocationCity());
			// Optional assertNotNull(activity.getLocationCountry());
			// Optional assertNotNull(activity.getLocationState());
			assertNotNull(activity.getManual());
			assertNotNull(activity.getMap());
			if (!activity.getManual() && !activity.getTrainer()) {
				StravaMapTest.validateMap(activity.getMap(), activity.getMap().getId(), activity.getMap().getResourceState(),
						activity);
			}
			assertNotNull(activity.getMaxSpeed());
			assertTrue(activity.getMaxSpeed() >= 0);
			assertNotNull(activity.getMovingTime());
			assertNotNull(activity.getName());
			assertNotNull(activity.getPhotoCount());
			assertNotNull(activity.getPrivateActivity());
			assertNull(activity.getSegmentEfforts());
			assertNull(activity.getSplitsMetric());
			assertNull(activity.getSplitsStandard());
			assertNotNull(activity.getStartDate());
			assertNotNull(activity.getStartDateLocal());
			// Optional (because sometimes GPS doesn't work properly)
			// assertNull(activity.getStartLatlng());
			assertNotNull(activity.getTimezone());
			assertNotNull(activity.getTotalElevationGain());
			assertNotNull(activity.getTrainer());
			assertNotNull(activity.getType());
			assertFalse(activity.getType() == StravaActivityType.UNKNOWN);
			// if (activity.getType() != StravaActivityType.RUN) {
			// assertNull(activity.getWorkoutType());
			// }
			if (activity.getWorkoutType() != null) {
				assertFalse(activity.getWorkoutType() == StravaWorkoutType.UNKNOWN);
			}
			if (activity.getPhotos() != null) {
				StravaActivityPhotosTest.validate(activity.getPhotos());
			}
			return;
		}
		if ((state == StravaResourceState.META) || (state == StravaResourceState.PRIVATE)) {
			assertNull(activity.getAchievementCount());
			assertNull(activity.getAthlete());
			assertNull(activity.getName());
			assertNull(activity.getDistance());
			assertNull(activity.getAthleteCount());
			assertNull(activity.getAverageCadence());
			assertNull(activity.getAverageHeartrate());
			assertNull(activity.getAverageSpeed());
			assertNull(activity.getAverageTemp());
			assertNull(activity.getAverageWatts());
			assertNull(activity.getBestEfforts());
			assertNull(activity.getCalories());
			assertNull(activity.getCommentCount());
			assertNull(activity.getCommute());
			assertNull(activity.getDescription());
			assertNull(activity.getDeviceWatts());
			assertNull(activity.getElapsedTime());
			assertNull(activity.getEndLatlng());
			assertNull(activity.getExternalId());
			assertNull(activity.getFlagged());
			assertNull(activity.getGear());
			assertNull(activity.getGearId());
			assertNull(activity.getHasKudoed());
			assertNull(activity.getKilojoules());
			assertNull(activity.getKudosCount());
			// assertNull(activity.getLocationCity());
			// assertNull(activity.getLocationCountry());
			// assertNull(activity.getLocationState());
			assertNull(activity.getManual());
			assertNull(activity.getMap());
			assertNull(activity.getMaxHeartrate());
			assertNull(activity.getMaxSpeed());
			assertNull(activity.getMovingTime());
			assertNull(activity.getName());
			assertNull(activity.getPhotoCount());
			assertNull(activity.getPrivateActivity());
			assertNull(activity.getSegmentEfforts());
			assertNull(activity.getSplitsMetric());
			assertNull(activity.getSplitsStandard());
			assertNull(activity.getStartDate());
			assertNull(activity.getStartDateLocal());
			assertNull(activity.getStartLatlng());
			assertNull(activity.getTimezone());
			assertNull(activity.getTotalElevationGain());
			assertNull(activity.getTrainer());
			assertNull(activity.getType());
			assertNull(activity.getWeightedAverageWatts());
			assertNull(activity.getWorkoutType());
			assertNull(activity.getPhotos());
			return;
		}
		fail("Unexpected activity state " + state + " for activity " + activity);
	}

	@Override
	protected Class<StravaActivity> getClassUnderTest() {
		return StravaActivity.class;
	}

	public static void validateList(final List<StravaActivity> activities) {
		for (final StravaActivity activity : activities) {
			validateActivity(activity);
		}
	}

}
