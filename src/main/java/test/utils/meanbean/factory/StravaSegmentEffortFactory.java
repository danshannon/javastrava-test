package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.service.standardtests.data.SegmentEffortDataUtils;

/**
 * Meanbean data factory
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentEffortFactory implements Factory<StravaSegmentEffort> {

	@Override
	public StravaSegmentEffort create() {
		return SegmentEffortDataUtils.testSegmentEffort(StravaResourceState.DETAILED);
	}

}
