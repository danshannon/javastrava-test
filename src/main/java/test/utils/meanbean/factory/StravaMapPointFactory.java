package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.api.v3.model.StravaMapPoint;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.service.standardtests.data.MapDataUtils;

/**
 * Mean bean factory for map point test data
 *
 * @author Dan Shannon
 *
 */
public class StravaMapPointFactory implements Factory<StravaMapPoint> {

	@Override
	public StravaMapPoint create() {
		return MapDataUtils.testMapPoint(StravaResourceState.DETAILED);
	}

}
