package test.api.model;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaActivityPhotos;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityPhotosTest extends BeanTest<StravaActivityPhotos> {
	/**
	 * Validate that an activity contains the documented structure and data
	 * 
	 * @param photos
	 *            Photos object to validate
	 */
	public static void validate(final StravaActivityPhotos photos) {
		assertNotNull(photos.getCount());
		if (photos.getPrimary() != null) {
			StravaPhotoTest.validatePhoto(photos.getPrimary(), photos.getPrimary().getId(), photos.getPrimary().getResourceState());
		}
	}

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaActivityPhotos> getClassUnderTest() {
		return StravaActivityPhotos.class;
	}

}
