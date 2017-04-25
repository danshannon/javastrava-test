package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.model.StravaPhoto;
import javastrava.model.reference.StravaResourceState;
import test.service.standardtests.data.PhotoDataUtils;

/**
 * Meanbean test data factory for strava photos
 *
 * @author Dan Shannon
 *
 */
public class StravaPhotoFactory implements Factory<StravaPhoto> {

	@Override
	public StravaPhoto create() {
		return PhotoDataUtils.testPhoto(StravaResourceState.DETAILED);
	}

}
