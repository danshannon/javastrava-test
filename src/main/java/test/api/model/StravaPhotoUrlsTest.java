package test.api.model;

import javastrava.api.v3.model.StravaPhotoUrls;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaPhotoUrlsTest extends BeanTest<StravaPhotoUrls> {

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaPhotoUrls> getClassUnderTest() {
		return StravaPhotoUrls.class;
	}

	public static void validate(StravaPhotoUrls urls) {
		//assertNotNull(urls.getUrl0());
	}
}
