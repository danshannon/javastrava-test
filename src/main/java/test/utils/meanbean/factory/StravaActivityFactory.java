package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.model.StravaActivity;
import javastrava.model.reference.StravaResourceState;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * MeanBean factory for Strava activities
 *
 * @author Dan Shannon
 *
 */
public class StravaActivityFactory implements Factory<StravaActivity> {

	@Override
	public StravaActivity create() {
		return ActivityDataUtils.testActivity(StravaResourceState.DETAILED);
	}

}
