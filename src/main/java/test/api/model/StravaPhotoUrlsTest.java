package test.api.model;

import javastrava.api.v3.model.StravaPhotoUrls;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaPhotoUrls}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaPhotoUrlsTest extends BeanTest<StravaPhotoUrls> {

	/**
	 * Validate the structure and content of an object
	 * 
	 * @param urls
	 *            The object to be validated
	 */
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
