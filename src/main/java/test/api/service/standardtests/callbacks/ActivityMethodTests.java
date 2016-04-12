/**
 *
 */
package test.api.service.standardtests.callbacks;

import javastrava.api.v3.model.StravaActivity;

/**
 * @author danshannon
 *
 */
public class ActivityMethodTests extends MethodTests<StravaActivity, Integer, Void> {

	/**
	 * @see test.api.service.standardtests.callbacks.MethodTests#creator()
	 */
	@Override
	protected CreateCallback<StravaActivity, Void> creator() {
		return ((strava, activity, parentId) -> {
			return strava.createManualActivity(activity);
		});
	}

	/**
	 * @see test.api.service.standardtests.callbacks.MethodTests#deleter()
	 */
	@Override
	protected DeleteCallback<StravaActivity, Integer, Void> deleter() {
		return ((strava, id, void_) -> {
			return strava.deleteActivity(id);
		});
	}

	/**
	 * @see test.api.service.standardtests.callbacks.MethodTests#getter()
	 */
	@Override
	protected GetCallback<StravaActivity, Integer> getter() {
		return ((strava, id) -> {
			return strava.getActivity(id);
		});
	}

}
