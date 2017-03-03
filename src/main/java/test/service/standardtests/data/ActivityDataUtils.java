package test.service.standardtests.data;

import java.time.LocalDateTime;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.service.standardtests.callbacks.GetCallback;

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
	 * @return Callback used to create activities on Strava
	 * @throws Exception
	 */
	public static CreateCallback<StravaActivity> creator() throws Exception {
		return ((strava, activity) -> {
			return strava.createManualActivity(activity);
		});
	}

	/**
	 * @return Callback used to delete activities on Strava
	 * @throws Exception
	 */
	public static DeleteCallback<StravaActivity> deleter() throws Exception {
		return ((strava, activity) -> {
			return strava.deleteActivity(activity);
		});
	}

	/**
	 * @return Callback used to get activities from Strava
	 * @throws Exception
	 */
	public static GetCallback<StravaActivity, Long> getter() throws Exception {
		return ((strava, id) -> {
			return strava.getActivity(id);
		});
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

}
