package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityPhotos;
import javastrava.api.v3.model.StravaActivityZone;
import javastrava.api.v3.model.StravaActivityZoneDistributionBucket;
import javastrava.api.v3.model.StravaBestRunningEffort;
import javastrava.api.v3.model.StravaLap;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSimilarActivities;
import javastrava.api.v3.model.StravaSimilarActivitiesTrend;
import javastrava.api.v3.model.StravaSplit;
import javastrava.api.v3.model.StravaUploadResponse;
import javastrava.api.v3.model.StravaVideo;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaActivityZoneType;
import javastrava.api.v3.model.reference.StravaMeasurementMethod;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaWorkoutType;
import test.api.model.StravaActivityPhotosTest;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.service.standardtests.callbacks.GetCallback;
import test.utils.TestUtils;

/**
 * <p>
 * Utilities for creating / deleting test data for activities
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ActivityDataUtils {
	/**
	 * Identifier of an activity that belongs to the authenticated athlete and includes segment efforts
	 */
	public static Long	ACTIVITY_WITH_EFFORTS;
	/**
	 * Identifier of an activity that belongs to the authenticated athlete and includes photos
	 */
	public static Long	ACTIVITY_WITH_PHOTOS;
	/**
	 * Identifier of an activity that belongs to the authenticated athlete and does not include photos
	 */
	public static Long	ACTIVITY_WITHOUT_PHOTOS;
	/**
	 * Identifier of an activity that belongs to the authenticated athlete
	 */
	public static Long	ACTIVITY_FOR_AUTHENTICATED_USER;
	/**
	 * Identifier of an activity that belongs to someone other than the authenticated athlete
	 */
	public static Long	ACTIVITY_FOR_UNAUTHENTICATED_USER;
	/**
	 * Invalid identifier of an activity
	 */
	public static Long	ACTIVITY_INVALID;
	/**
	 * Identifier of an activity that has comments
	 */
	public static Long	ACTIVITY_WITH_COMMENTS;
	/**
	 * Identifier of an activity that does not have comments
	 */
	public static Long	ACTIVITY_WITHOUT_COMMENTS;
	/**
	 * Identifier of an activity that has kudos
	 */
	public static Long	ACTIVITY_WITH_KUDOS;
	/**
	 * Identifier of an activity that does not have kudos
	 */
	public static Long	ACTIVITY_WITHOUT_KUDOS;
	/**
	 * Identifier of an activity that has laps
	 */
	public static Long	ACTIVITY_WITH_LAPS;
	/**
	 * Identifier of an activity that does not have laps
	 */
	public static Long	ACTIVITY_WITHOUT_LAPS;
	/**
	 * Identifier of an activity that has heart rate and/or power data
	 */
	public static Long	ACTIVITY_WITH_ZONES;
	/**
	 * Identifier of an activity that does not have heart rate or power data
	 */
	public static Long	ACTIVITY_WITHOUT_ZONES;
	/**
	 * Identifier of a private activity that belongs to a user other than the authenticated athlete
	 */
	public static Long	ACTIVITY_PRIVATE_OTHER_USER;
	/**
	 * Identifier of a private activity that belongs to the authenticated athlete
	 */
	public static Long	ACTIVITY_PRIVATE;
	/**
	 * Identifier of a private activity that belongs to the authenticated athlete and has photos
	 */
	public static Long	ACTIVITY_PRIVATE_WITH_PHOTOS;
	/**
	 * Identifier of a private activity that belongs to the authenticated athlete and has kudos
	 */
	public static Long	ACTIVITY_PRIVATE_WITH_KUDOS;
	/**
	 * Identifier of a private activity that belongs to the authenticated athlete and has laps
	 */
	public static Long	ACTIVITY_PRIVATE_WITH_LAPS;
	/**
	 * Identifier of a private activity that belongs to the authenticated athlete and has related activities
	 */
	public static Long	ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES;
	/**
	 * Identifier of an activity that belongs to the authenticated athlete and has related activities
	 */
	public static Long	ACTIVITY_WITH_RELATED_ACTIVITIES;
	/**
	 * Identifier of an activity that belongs to the authenticated athlete and does not have related activities
	 */
	public static Long	ACTIVITY_WITHOUT_RELATED_ACTIVITIES;
	/**
	 * Identifier of an activity that is a run belonging to a user who is not the authenticated athlete
	 */
	public static Long	ACTIVITY_RUN_OTHER_USER;
	/**
	 * Identifier of an activity that is a run with segment efforts
	 */
	public static Long	ACTIVITY_RUN_WITH_SEGMENTS;

	static {
		ACTIVITY_WITH_EFFORTS = TestUtils.longProperty("test.activityWithEfforts"); //$NON-NLS-1$
		ACTIVITY_WITH_PHOTOS = TestUtils.longProperty("test.activityWithPhotos"); //$NON-NLS-1$
		ACTIVITY_WITHOUT_PHOTOS = TestUtils.longProperty("test.activityWithoutPhotos"); //$NON-NLS-1$
		ACTIVITY_WITH_COMMENTS = TestUtils.longProperty("test.activityWithComments"); //$NON-NLS-1$
		ACTIVITY_WITHOUT_COMMENTS = TestUtils.longProperty("test.activityWithoutComments"); //$NON-NLS-1$
		ACTIVITY_WITH_KUDOS = TestUtils.longProperty("test.activityWithKudos"); //$NON-NLS-1$
		ACTIVITY_WITHOUT_KUDOS = TestUtils.longProperty("test.activityWithoutKudos"); //$NON-NLS-1$
		ACTIVITY_WITH_LAPS = TestUtils.longProperty("test.activityWithLaps"); //$NON-NLS-1$
		ACTIVITY_WITHOUT_LAPS = TestUtils.longProperty("test.activityWithoutLaps"); //$NON-NLS-1$
		ACTIVITY_WITH_ZONES = TestUtils.longProperty("test.activityWithZones"); //$NON-NLS-1$
		ACTIVITY_WITHOUT_ZONES = TestUtils.longProperty("test.activityWithoutZones"); //$NON-NLS-1$
		ACTIVITY_FOR_AUTHENTICATED_USER = TestUtils.longProperty("test.activityBelongingToAuthenticatedUser"); //$NON-NLS-1$
		ACTIVITY_FOR_UNAUTHENTICATED_USER = TestUtils.longProperty("test.activityBelongingToUnauthenticatedUser"); //$NON-NLS-1$
		ACTIVITY_INVALID = TestUtils.longProperty("test.activityInvalid"); //$NON-NLS-1$
		ACTIVITY_PRIVATE_OTHER_USER = TestUtils.longProperty("test.activityPrivateOtherUser"); //$NON-NLS-1$
		ACTIVITY_PRIVATE = TestUtils.longProperty("test.activityPrivateAuthenticatedUser"); //$NON-NLS-1$
		ACTIVITY_PRIVATE_WITH_PHOTOS = TestUtils.longProperty("test.activityPrivatePhotos"); //$NON-NLS-1$
		ACTIVITY_PRIVATE_WITH_KUDOS = TestUtils.longProperty("test.activityPrivateKudos"); //$NON-NLS-1$
		ACTIVITY_PRIVATE_WITH_LAPS = TestUtils.longProperty("test.activityPrivateLaps"); //$NON-NLS-1$
		ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES = TestUtils.longProperty("test.activityPrivateRelated"); //$NON-NLS-1$
		ACTIVITY_WITH_RELATED_ACTIVITIES = TestUtils.longProperty("test.ActivityWithRelatedActivities"); //$NON-NLS-1$
		ACTIVITY_WITHOUT_RELATED_ACTIVITIES = TestUtils.longProperty("test.ActivityWithoutRelatedActivities"); //$NON-NLS-1$
		ACTIVITY_RUN_OTHER_USER = TestUtils.longProperty("test.activityRunOtherUser"); //$NON-NLS-1$
		ACTIVITY_RUN_WITH_SEGMENTS = TestUtils.longProperty("test.activityRunWithSegments"); //$NON-NLS-1$

	}

	private static Random random = new Random();

	private static TextProducer text = Fairy.create().textProducer();

	/**
	 * @param name
	 *            Name to give to the activity
	 * @return Activity created
	 */
	public static StravaActivity createDefaultActivity(final String name) {
		final StravaActivity activity = new StravaActivity();
		activity.setName(name);
		activity.setType(StravaActivityType.RIDE);
		activity.setStartDateLocal(LocalDateTime.now());
		activity.setElapsedTime(new Integer(1000));
		activity.setDescription("Created by Strava API v3 Java"); //$NON-NLS-1$
		activity.setDistance(new Float(1000.1F));
		return activity;
	}

	/**
	 * @return Callback used to create activities on Strava
	 */
	public static CreateCallback<StravaActivity> creator() {
		return ((strava, activity) -> {
			return strava.createManualActivity(activity);
		});
	}

	/**
	 * @return Callback used to delete activities on Strava
	 */
	public static DeleteCallback<StravaActivity> deleter() {
		return ((strava, activity) -> {
			return strava.deleteActivity(activity);
		});
	}

	/**
	 * Create an invalid activity
	 *
	 * @return Activity created
	 */
	public static StravaActivity generateInvalidObject() {
		final StravaActivity activity = createDefaultActivity("CreateManualActivityTest.invalidActivity"); //$NON-NLS-1$
		// Start date is required
		activity.setStartDateLocal(null);
		return activity;
	}

	/**
	 * Create a valid activity
	 *
	 * @return Activity created
	 */
	public static StravaActivity generateValidObject() {
		return createDefaultActivity("CreateManualActivityTest.validActivity"); //$NON-NLS-1$
	}

	/**
	 * @return Callback used to get activities from Strava
	 */
	public static GetCallback<StravaActivity, Long> getter() {
		return ((strava, id) -> {
			return strava.getActivity(id);
		});
	}

	/**
	 * Generate a valid {@link StravaActivity}
	 *
	 * @param resourceState
	 *            The resource state of the object to be created
	 * @return The generated activity
	 */
	@SuppressWarnings("boxing")
	public static StravaActivity testActivity(StravaResourceState resourceState) {
		if ((resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			throw new IllegalArgumentException();
		}

		final StravaActivity activity = new StravaActivity();

		// Set the attributes common to all resource states
		activity.setId(random.nextLong());
		activity.setResourceState(resourceState);

		// If this is a PRIVATE or META activity then return it now
		if ((resourceState == StravaResourceState.META) || (resourceState == StravaResourceState.PRIVATE)) {
			return activity;
		}

		// Set attributes common to both SUMMARY and DETAILED resource states
		activity.setAchievementCount(random.nextInt(10000));
		activity.setAthlete(AthleteDataUtils.testAthlete(StravaResourceState.SUMMARY));
		activity.setAthleteCount(random.nextInt(10000));
		activity.setAverageCadence(random.nextFloat() * 100);
		activity.setAverageHeartrate(random.nextFloat() * 150);
		activity.setAverageSpeed(random.nextFloat() * 40);
		activity.setAverageTemp((random.nextFloat() * 60) - 15);
		activity.setAverageWatts(random.nextFloat() * 500);
		activity.setCommentCount(random.nextInt(30));
		activity.setCommute(random.nextBoolean());
		activity.setDeviceWatts(random.nextBoolean());
		activity.setDistance(random.nextFloat() * 200000);
		activity.setElapsedTime(random.nextInt(12 * 3600));
		activity.setElevHigh(random.nextFloat() * 5000);
		activity.setElevLow(random.nextFloat() * activity.getElevHigh());
		activity.setEndLatlng(MapDataUtils.testMapPoint(StravaResourceState.DETAILED));
		activity.setExternalId(text.randomString(100));
		activity.setFlagged(random.nextBoolean());
		activity.setGearId(GearDataUtils.testGear(StravaResourceState.META, RefDataUtils.randomGearType()).getId());
		activity.setHasHeartrate(random.nextBoolean());
		activity.setHasKudoed(random.nextBoolean());
		activity.setInstagramPrimaryPhoto(text.randomString(100));
		activity.setKilojoules(random.nextFloat() * 10000);
		activity.setKudosCount(random.nextInt(100));
		activity.setManual(random.nextBoolean());
		activity.setMap(MapDataUtils.testMap(StravaResourceState.DETAILED));
		activity.setMaxHeartrate(random.nextInt(150) + 70);
		activity.setMaxSpeed(random.nextFloat() * 100);
		activity.setMaxWatts(random.nextInt(1000));
		activity.setMovingTime(random.nextInt(activity.getElapsedTime()));
		activity.setName(text.sentence());
		activity.setPhotoCount(random.nextInt(20));
		activity.setPrCount(random.nextInt(100));
		activity.setPrivateActivity(random.nextBoolean());
		activity.setSimilarActivities(testSimilarActivities(StravaResourceState.DETAILED));
		activity.setStartDate(DateUtils.zonedDateTime());
		activity.setStartDateLocal(DateUtils.localDateTime());
		activity.setStartLatitude((random.nextFloat() * 180) - 90);
		activity.setStartLatlng(MapDataUtils.testMapPoint(StravaResourceState.DETAILED));
		activity.setStartLongitude((random.nextFloat() * 360) - 180);
		activity.setSufferScore(random.nextInt(400));
		activity.setTimezone(text.word());
		activity.setTotalElevationGain(random.nextFloat() * 9000);
		activity.setTotalPhotoCount(random.nextInt(20));
		activity.setTrainer(random.nextBoolean());
		activity.setType(RefDataUtils.randomActivityType());
		activity.setUploadId(random.nextLong());
		activity.setUtcOffset(text.word());
		activity.setVideo(testVideo(StravaResourceState.DETAILED));
		activity.setWeightedAverageWatts(random.nextFloat() * 500);
		activity.setWorkoutType(RefDataUtils.randomWorkoutType());

		// If this is a SUMMARY representation, return it now
		if (resourceState == StravaResourceState.SUMMARY) {
			return activity;
		}

		// Add the attributes specific to DETAILED representations
		activity.setCalories(random.nextFloat() * 2000);
		activity.setDescription(text.paragraph());
		activity.setGear(GearDataUtils.testGear(StravaResourceState.SUMMARY, RefDataUtils.randomGearType()));
		activity.setSegmentEfforts(SegmentEffortDataUtils.testSegmentEffortList(StravaResourceState.SUMMARY));
		activity.setSplitsMetric(testSplitsList(StravaResourceState.DETAILED, StravaMeasurementMethod.METRIC));
		activity.setSplitsStandard(testSplitsList(StravaResourceState.DETAILED, StravaMeasurementMethod.IMPERIAL));
		activity.setLaps(testLapsList(StravaResourceState.SUMMARY));
		activity.setBestEfforts(ActivityDataUtils.testBestEffortList(resourceState));
		activity.setDeviceName(text.word());
		activity.setEmbedToken(text.randomString(64));
		activity.setPhotos(testActivityPhotos(StravaResourceState.SUMMARY));

		return activity;
	}

	/**
	 * Generate test data for StravaActivityPhotos
	 *
	 * @param resourceState
	 *            The resource state the generated data should be in
	 * @return The generated test data
	 */
	@SuppressWarnings("boxing")
	public static StravaActivityPhotos testActivityPhotos(StravaResourceState resourceState) {
		final StravaActivityPhotos photos = new StravaActivityPhotos();

		photos.setCount(random.nextInt(20));
		photos.setPrimary(PhotoDataUtils.testPhoto(resourceState));
		photos.setUsePrimaryPhoto(text.word());

		return photos;
	}

	private static List<StravaSplit> testSplitsList(StravaResourceState resourceState, StravaMeasurementMethod measurementMethod) {
		final List<StravaSplit> splits = new ArrayList<>();
		final int entries = random.nextInt(1000);
		for (int i = 0; i < entries; i++) {
			splits.add(testSplit(resourceState, measurementMethod));
		}
		return splits;
	}

	@SuppressWarnings("boxing")
	private static StravaSplit testSplit(StravaResourceState resourceState, StravaMeasurementMethod measurementMethod) {
		final StravaSplit split = new StravaSplit();

		split.setAverageHeartrate(random.nextFloat() * 175);
		split.setAverageSpeed(random.nextFloat() * 45);
		split.setDistance(random.nextFloat() * 10000);
		split.setElapsedTime(random.nextInt(3600));
		split.setElevationDifference(random.nextFloat() * 1000);
		split.setMovingTime(random.nextInt(3600));
		split.setPaceZone(random.nextInt(5));
		split.setSplit(random.nextInt(100));

		return split;
	}

	@SuppressWarnings("boxing")
	private static StravaSimilarActivities testSimilarActivities(StravaResourceState resourceState) {
		final StravaSimilarActivities stats = new StravaSimilarActivities();

		stats.setAverageSpeed(random.nextFloat() * 45);
		stats.setEffortCount(random.nextInt(50));
		stats.setFrequencyMilestone(text.word());
		stats.setMaxAverageSpeed(random.nextFloat() * 50);
		stats.setMidAverageSpeed(random.nextFloat() * 50);
		stats.setMinAverageSpeed(random.nextFloat() * 50);
		stats.setPrRank(random.nextInt(100));
		stats.setResourceState(resourceState);
		stats.setTrend(testSimilarActivitiesTrend());

		return stats;
	}

	/**
	 * @return Test data
	 */
	@SuppressWarnings("boxing")
	public static StravaSimilarActivitiesTrend testSimilarActivitiesTrend() {
		final StravaSimilarActivitiesTrend trend = new StravaSimilarActivitiesTrend();

		trend.setCurrentActivityIndex(random.nextInt(100));
		trend.setDirection(random.nextInt(8));
		trend.setMaxSpeed(random.nextFloat() * 100);
		trend.setMidSpeed(random.nextFloat() * 100);
		trend.setMinSpeed(random.nextFloat() * 100);
		final List<Float> speeds = new ArrayList<>();
		final int entries = random.nextInt(100);
		for (int i = 0; i < entries; i++) {
			speeds.add(random.nextFloat() * 50);
		}
		trend.setSpeeds(speeds);
		return trend;

	}

	private static List<StravaLap> testLapsList(StravaResourceState resourceState) {
		final List<StravaLap> list = new ArrayList<>();
		final int entries = random.nextInt(10000);
		for (int i = 0; i < entries; i++) {
			list.add(testLap(resourceState));
		}
		return list;
	}

	@SuppressWarnings("boxing")
	private static StravaLap testLap(StravaResourceState resourceState) {
		if ((resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			throw new IllegalArgumentException();
		}

		final StravaLap lap = new StravaLap();

		// Set attributes that are common to all resource states
		lap.setId(random.nextLong());
		lap.setResourceState(resourceState);

		// If this is a META or PRIVATE state, return it now
		if ((resourceState == StravaResourceState.META) || (resourceState == StravaResourceState.PRIVATE)) {
			return lap;
		}

		lap.setActivity(ActivityDataUtils.testActivity(StravaResourceState.META));
		lap.setAthlete(AthleteDataUtils.testAthlete(StravaResourceState.META));
		lap.setAverageCadence(random.nextFloat() * 100);
		lap.setAverageHeartrate(random.nextFloat() * 175);
		lap.setAverageSpeed(random.nextFloat() * 45);
		lap.setAverageWatts(random.nextFloat() * 500);
		lap.setDeviceWatts(random.nextBoolean());
		lap.setDistance(random.nextFloat() * 10000);
		lap.setElapsedTime(random.nextInt(3600));
		lap.setEndIndex(random.nextInt(50000));
		lap.setId(random.nextLong());
		lap.setLapIndex(random.nextInt(100));
		lap.setMaxHeartrate(random.nextFloat() * 220);
		lap.setMaxSpeed(random.nextFloat() * 100);
		lap.setMovingTime(random.nextInt(3600));
		lap.setName(text.word(3));
		lap.setPaceZone(random.nextInt(5));
		lap.setSplit(text.word());
		lap.setStartDate(DateUtils.zonedDateTime());
		lap.setStartIndex(random.nextInt(10000));
		lap.setTotalElevationGain(random.nextFloat() * 9000);

		return lap;
	}

	/**
	 * @param resourceState
	 *            Resource state the video should be created in
	 * @return The generated video
	 */
	@SuppressWarnings("boxing")
	public static StravaVideo testVideo(StravaResourceState resourceState) {
		if ((resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			throw new IllegalArgumentException();
		}

		final StravaVideo video = new StravaVideo();

		video.setId(random.nextInt(Integer.MAX_VALUE));
		video.setResourceState(resourceState);

		// If this is a META or PRIVATE video, return it now
		if ((resourceState == StravaResourceState.META) || (resourceState == StravaResourceState.PRIVATE)) {
			return video;
		}

		video.setBadgeImageUrl(text.randomString(100));

		return video;
	}

	/**
	 * Generate a {@link StravaBestRunningEffort} with the required resource state
	 *
	 * @param resourceState
	 *            The resource state
	 * @return The generated effort
	 */
	@SuppressWarnings("boxing")
	public static StravaBestRunningEffort testBestEffort(StravaResourceState resourceState) {
		if ((resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			throw new IllegalArgumentException();
		}

		final StravaBestRunningEffort bestEffort = new StravaBestRunningEffort();

		// Set attributes common to all types
		bestEffort.setId(random.nextInt(Integer.MAX_VALUE));
		bestEffort.setResourceState(resourceState);

		// If we're creating a PRIVATE or META effort, return it now
		if ((resourceState == StravaResourceState.META) || (resourceState == StravaResourceState.PRIVATE)) {
			return bestEffort;
		}

		// Set the rest of the attributes
		bestEffort.setActivity(testActivity(StravaResourceState.META));
		bestEffort.setAthlete(AthleteDataUtils.testAthlete(StravaResourceState.META));
		bestEffort.setDistance(random.nextFloat() * 5000);
		bestEffort.setElapsedTime(random.nextInt(12 * 3600));
		bestEffort.setKomRank(random.nextInt(1000));
		bestEffort.setMovingTime(random.nextInt(12 * 3600));
		bestEffort.setName(text.sentence());
		bestEffort.setPrRank(random.nextInt(100));
		bestEffort.setSegment(SegmentDataUtils.testSegment(StravaResourceState.META));
		bestEffort.setStartDateLocal(DateUtils.localDateTime());

		return bestEffort;
	}

	/**
	 * Generate a list of {@link StravaBestRunningEffort}s with the required resource state
	 *
	 * @param resourceState
	 *            The resource state
	 * @return The list of generated efforts
	 */
	public static List<StravaBestRunningEffort> testBestEffortList(StravaResourceState resourceState) {
		final List<StravaBestRunningEffort> list = new ArrayList<>();
		final int entries = random.nextInt(10000);
		for (int i = 0; i < entries; i++) {
			list.add(testBestEffort(resourceState));
		}
		return list;
	}

	/**
	 * Validate that the given activity's structure and data is as expected
	 *
	 * @param activity
	 *            The activity to validate
	 */
	public static void validate(final StravaActivity activity) {
		ActivityDataUtils.validate(activity, activity.getId(), activity.getResourceState());
	}

	/**
	 * Validate that the given activity's structure and data is as expected
	 *
	 * @param activity
	 *            The activity to validate
	 * @param id
	 *            Identifier
	 * @param state
	 *            Resource state
	 */
	@SuppressWarnings("boxing")
	public static void validate(final StravaActivity activity, final Long id, final StravaResourceState state) {
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
			assertTrue((activity.getAthlete().getResourceState() == StravaResourceState.META) || (activity.getAthlete().getResourceState() == StravaResourceState.SUMMARY));
			AthleteDataUtils.validateAthlete(activity.getAthlete(), activity.getAthlete().getId(), activity.getAthlete().getResourceState());
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
					ActivityDataUtils.validateBestEffort(effort, effort.getId(), effort.getResourceState());
				}
				if (!activity.getManual()) {
					assertNotNull(activity.getSplitsMetric());
					for (final StravaSplit split : activity.getSplitsMetric()) {
						ActivityDataUtils.validateSplit(split);
					}
					assertNotNull(activity.getSplitsStandard());
					for (final StravaSplit split : activity.getSplitsStandard()) {
						ActivityDataUtils.validateSplit(split);
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
				GearDataUtils.validateGear(activity.getGear(), activity.getGearId(), activity.getGear().getResourceState());
			}
			assertNotNull(activity.getHasKudoed());
			if ((activity.getType() == StravaActivityType.RIDE) && !activity.getManual() && !activity.getTrainer() && (activity.getCalories() != null)) {
				assertNotNull(activity.getKilojoules());
			}
			assertNotNull(activity.getKudosCount());
			// Optional assertNotNull(activity.getLocationCity());
			// Optional assertNotNull(activity.getLocationCountry());
			// Optional assertNotNull(activity.getLocationState());
			assertNotNull(activity.getManual());
			assertNotNull(activity.getMap());
			if (!activity.getManual() && !activity.getTrainer()) {
				MapDataUtils.validateMap(activity.getMap(), activity.getMap().getId(), activity.getMap().getResourceState(), activity);
			}
			assertNotNull(activity.getMaxSpeed());
			assertTrue(activity.getMaxSpeed() >= 0);
			assertNotNull(activity.getMovingTime());
			assertNotNull(activity.getName());
			assertNotNull(activity.getPhotoCount());
			assertNotNull(activity.getPrivateActivity());
			assertNotNull(activity.getSegmentEfforts());
			for (final StravaSegmentEffort effort : activity.getSegmentEfforts()) {
				SegmentEffortDataUtils.validateSegmentEffort(effort, effort.getId(), effort.getResourceState());
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
				ActivityDataUtils.validateVideo(activity.getVideo());
			}
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(activity.getAchievementCount());
			assertNotNull(activity.getAthlete());
			assertTrue((activity.getAthlete().getResourceState() == StravaResourceState.META) || (activity.getAthlete().getResourceState() == StravaResourceState.SUMMARY));
			AthleteDataUtils.validateAthlete(activity.getAthlete(), activity.getAthlete().getId(), activity.getAthlete().getResourceState());
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
			if ((activity.getType() == StravaActivityType.RIDE) && !activity.getManual() && !activity.getTrainer() && (activity.getCalories() != null)) {
				assertNotNull(activity.getKilojoules());
			}
			assertNotNull(activity.getKudosCount());
			// Optional assertNotNull(activity.getLocationCity());
			// Optional assertNotNull(activity.getLocationCountry());
			// Optional assertNotNull(activity.getLocationState());
			assertNotNull(activity.getManual());
			assertNotNull(activity.getMap());
			if (!activity.getManual() && !activity.getTrainer()) {
				MapDataUtils.validateMap(activity.getMap(), activity.getMap().getId(), activity.getMap().getResourceState(), activity);
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
		fail("Unexpected activity state " + state + " for activity " + activity); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * <p>
	 * Validate structure and content of the bucket
	 * </p>
	 *
	 * @param bucket
	 *            Bucket to be validated
	 */
	public static void validate(final StravaActivityZoneDistributionBucket bucket) {
		assertNotNull(bucket);
		assertNotNull(bucket.getMax());
		assertNotNull(bucket.getMin());
		assertNotNull(bucket.getTime());
	}

	/**
	 * @param zone
	 *            The zone to validate
	 */
	@SuppressWarnings("boxing")
	public static void validateActivityZone(final StravaActivityZone zone) {
		assertNotNull(zone);

		// Optional assertNotNull(zone.getCustomZones());
		assertNotNull(zone.getDistributionBuckets());
		for (final StravaActivityZoneDistributionBucket bucket : zone.getDistributionBuckets()) {
			validate(bucket);
		}
		if (zone.getType() == StravaActivityZoneType.HEARTRATE) {
			if (zone.getMax() != null) {
				assertTrue(zone.getMax() >= 0);
			}
		} else {
			assertNull(zone.getMax());
		}
		if (zone.getPoints() != null) {
			assertTrue(zone.getPoints() >= 0);
		}
		if (zone.getScore() != null) {
			assertTrue(zone.getScore() >= 0);
		}
		assertNotNull(zone.getType());
		assertFalse(zone.getType() == StravaActivityZoneType.UNKNOWN);
		return;
	}

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
			AthleteDataUtils.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(), effort.getAthlete().getResourceState());
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			// Optional assertNotNull(effort.getKomRank());
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			// Optional assertNotNull(effort.getPrRank());
			// Optional assertNotNull(effort.getSegment());
			if (effort.getSegment() != null) {
				SegmentDataUtils.validateSegment(effort.getSegment(), effort.getSegment().getId(), effort.getSegment().getResourceState());
			}
			assertNotNull(effort.getStartDate());
			assertNotNull(effort.getStartDateLocal());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(effort.getActivity());
			// NB Don't validate the activity - that way lies 1 Infinite Loop
			assertNotNull(effort.getAthlete());
			AthleteDataUtils.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(), effort.getAthlete().getResourceState());
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			// Optional assertNotNull(effort.getKomRank());
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			// Optional assertNotNull(effort.getPrRank());
			// Optional assertNotNull(effort.getSegment());
			if (effort.getSegment() != null) {
				SegmentDataUtils.validateSegment(effort.getSegment(), effort.getSegment().getId(), effort.getSegment().getResourceState());
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

	/**
	 * Validate a single lap
	 *
	 * @param lap
	 *            The lap to be validated
	 */
	public static void validateLap(final StravaLap lap) {
		ActivityDataUtils.validateLap(lap, lap.getId(), lap.getResourceState());
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
			validate(lap.getActivity(), lap.getActivity().getId(), lap.getActivity().getResourceState());
			assertNotNull(lap.getAthlete());
			AthleteDataUtils.validateAthlete(lap.getAthlete(), lap.getAthlete().getId(), lap.getAthlete().getResourceState());
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
			validate(lap.getActivity(), lap.getActivity().getId(), lap.getActivity().getResourceState());
			assertNotNull(lap.getAthlete());
			AthleteDataUtils.validateAthlete(lap.getAthlete(), lap.getAthlete().getId(), lap.getAthlete().getResourceState());
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
	public static void validateLapsList(final List<StravaLap> laps) {
		for (final StravaLap lap : laps) {
			validateLap(lap);
		}
	}

	/**
	 * Validate the structure and content of a split
	 *
	 * @param split
	 *            The split to be validated
	 */
	public static void validateSplit(final StravaSplit split) {
		assertNotNull(split);
		assertNotNull(split.getDistance());
		assertNotNull(split.getElapsedTime());
		assertNotNull(split.getElevationDifference());
		assertNotNull(split.getMovingTime());
		assertNotNull(split.getSplit());

	}

	/**
	 * Validate the structure and content of a response
	 *
	 * @param response
	 *            The response to be validated
	 */
	public static void validateUploadResponse(final StravaUploadResponse response) {
		assertNotNull(response);
		assertNotNull(response.getActivityId());
		assertNotNull(response.getId());
		assertNotNull(response.getStatus());

	}

	/**
	 * Validate the structure and content of a video
	 *
	 * @param video
	 *            The video to be validated
	 */
	public static void validateVideo(final StravaVideo video) {
		assertNotNull(video.getId());
		assertNotNull(video.getBadgeImageUrl());
		assertNotNull(video.getStillImageUrl());
	}

}
