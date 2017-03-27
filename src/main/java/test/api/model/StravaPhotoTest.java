package test.api.model;

import javastrava.api.v3.model.StravaPhoto;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaPhotoTest extends BeanTest<StravaPhoto> {

	@Override
	protected Class<StravaPhoto> getClassUnderTest() {
		return StravaPhoto.class;
	}
}
