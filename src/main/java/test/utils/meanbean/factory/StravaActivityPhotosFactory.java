package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.model.StravaActivityPhotos;
import javastrava.model.reference.StravaResourceState;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * Mean bean data factory for StravaActivityPhotos
 *
 * @author Dan Shannon
 *
 */
public class StravaActivityPhotosFactory implements Factory<StravaActivityPhotos> {

	@Override
	public StravaActivityPhotos create() {
		return ActivityDataUtils.testActivityPhotos(StravaResourceState.DETAILED);
	}

}
