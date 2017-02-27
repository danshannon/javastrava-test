package test.api.service.standardtests.data;

import javastrava.api.v3.model.StravaActivity;
import test.api.service.standardtests.callbacks.CreateCallback;
import test.api.service.standardtests.callbacks.DeleteCallback;
import test.api.service.standardtests.callbacks.GetCallback;
import test.utils.TestUtils;

public class ActivityDataUtils {
	public static CreateCallback<StravaActivity> creator() throws Exception {
		return ((strava, activity) -> {
			return strava.createManualActivity(activity);
		});
	}

	public static DeleteCallback<StravaActivity> deleter() throws Exception {
		return ((strava, activity) -> {
			return strava.deleteActivity(activity);
		});
	}

	public static GetCallback<StravaActivity, Long> getter() throws Exception {
		return ((strava, id) -> {
			return strava.getActivity(id);
		});
	}

	public static StravaActivity generateValidObject() {
		return TestUtils.createDefaultActivity("CreateManualActivityTest.validActivity"); //$NON-NLS-1$
	}

	public static StravaActivity generateInvalidObject() {
		final StravaActivity activity = TestUtils.createDefaultActivity("CreateManualActivityTest.invalidActivity"); //$NON-NLS-1$
		// Start date is required
		activity.setStartDateLocal(null);
		return activity;
	}

}
