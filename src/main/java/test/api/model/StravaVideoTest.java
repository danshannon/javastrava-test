/**
 *
 */
package test.api.model;

import javastrava.api.v3.model.StravaVideo;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaVideo}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaVideoTest extends BeanTest<StravaVideo> {

	@Override
	protected Class<StravaVideo> getClassUnderTest() {
		return StravaVideo.class;
	}

}
