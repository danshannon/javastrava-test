package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.api.v3.model.StravaSimilarActivities;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * Mean bean test data factory
 *
 * @author Dan Shannon
 *
 */
public class StravaSimilarActivitiesFactory implements Factory<StravaSimilarActivities> {

	@Override
	public StravaSimilarActivities create() {
		return ActivityDataUtils.testSimilarActivities(StravaResourceState.DETAILED);
	}

}
