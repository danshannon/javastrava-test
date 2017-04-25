package test.model;

import javastrava.model.StravaPhotoUrls;
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

	@Override
	protected Class<StravaPhotoUrls> getClassUnderTest() {
		return StravaPhotoUrls.class;
	}
}
