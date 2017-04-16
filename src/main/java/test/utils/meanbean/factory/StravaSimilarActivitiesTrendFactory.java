package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.api.v3.model.StravaSimilarActivitiesTrend;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * Mean bean data factory
 *
 * @author Dan Shannon
 *
 */
public class StravaSimilarActivitiesTrendFactory implements Factory<StravaSimilarActivitiesTrend> {

	@Override
	public StravaSimilarActivitiesTrend create() {
		return ActivityDataUtils.testSimilarActivitiesTrend();
	}

}
