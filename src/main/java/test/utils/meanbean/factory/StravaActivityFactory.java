package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaResourceState;
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
