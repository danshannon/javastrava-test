package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.api.v3.model.StravaPhotoSizes;
import test.service.standardtests.data.PhotoDataUtils;

/**
 * MeanBean factory for Strava photo sizes objects
 *
 * @author Dan Shannon
 *
 */
public class StravaPhotoSizesFactory implements Factory<StravaPhotoSizes> {

	@Override
	public StravaPhotoSizes create() {
		return PhotoDataUtils.testPhotoSizes();
	}

}
