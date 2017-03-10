package test.service.standardtests.data;

import java.time.LocalDateTime;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
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

}
