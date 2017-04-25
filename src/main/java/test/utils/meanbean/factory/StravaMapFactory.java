package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.model.StravaMap;
import javastrava.model.reference.StravaResourceState;
import test.service.standardtests.data.MapDataUtils;

/**
 * Mean bean factory for map test data
 *
 * @author Dan Shannon
 *
 */
public class StravaMapFactory implements Factory<StravaMap> {

	@Override
	public StravaMap create() {
		return MapDataUtils.testMap(StravaResourceState.DETAILED);
	}

}
