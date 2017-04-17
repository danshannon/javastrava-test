package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.api.v3.model.StravaAthleteSegmentStats;
import test.service.standardtests.data.SegmentEffortDataUtils;

/**
 * Mean bean data factory
 *
 * @author Dan Shannon
 *
 */
public class StravaAthleteSegmentStatsFactory implements Factory<StravaAthleteSegmentStats> {

	@Override
	public StravaAthleteSegmentStats create() {
		return SegmentEffortDataUtils.testAthleteSegmentStats();
	}

}
