package test.api.model;

import javastrava.api.v3.model.StravaPhotoUrls;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaPhotoUrlsTest extends BeanTest<StravaPhotoUrls> {

	public static void validate(final StravaPhotoUrls urls) {
		// assertNotNull(urls.getUrl0());
	}

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaPhotoUrls> getClassUnderTest() {
		return StravaPhotoUrls.class;
	}
}
