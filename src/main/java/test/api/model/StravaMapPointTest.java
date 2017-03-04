package test.api.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javastrava.api.v3.model.StravaMapPoint;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaMapPoint}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaMapPointTest extends BeanTest<StravaMapPoint> {

	/**
	 * Validate the structure and content of a map point
	 * 
	 * @param point
	 *            The map point to be validated
	 */
	@SuppressWarnings("boxing")
	public static void validate(final StravaMapPoint point) {
		assertNotNull(point);
		assertNotNull(point.getLatitude());
		assertTrue((point.getLatitude() <= 90) && (point.getLatitude() >= -90));
		assertNotNull(point.getLongitude());
		assertTrue((point.getLongitude() <= 180) && (point.getLongitude() >= -180));

	}

	@Override
	protected Class<StravaMapPoint> getClassUnderTest() {
		return StravaMapPoint.class;
	}

}
