/**
 *
 */
package test.api.model;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaVideo;
import test.utils.BeanTest;

/**
 * @author danshannon
 *
 */
public class StravaVideoTest extends BeanTest<StravaVideo> {

	public static void validate(final StravaVideo video) {
		assertNotNull(video.getId());
		assertNotNull(video.getBadgeImageUrl());
		assertNotNull(video.getStillImageUrl());
	}

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaVideo> getClassUnderTest() {
		return StravaVideo.class;
	}

}
