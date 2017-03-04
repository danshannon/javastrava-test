/**
 *
 */
package test.api.model;

import static org.junit.Assert.assertNotNull;

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

	/**
	 * Validate the structure and content of a video
	 *
	 * @param video
	 *            The video to be validated
	 */
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
