/**
 *
 */
package test.api.model;

import javastrava.api.v3.model.StravaPhotoSizes;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaPhotoSizes}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaPhotoSizesTest extends BeanTest<StravaPhotoSizes> {

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaPhotoSizes> getClassUnderTest() {
		return StravaPhotoSizes.class;
	}

}
