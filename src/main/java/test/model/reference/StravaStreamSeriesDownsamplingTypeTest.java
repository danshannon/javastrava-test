package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaStreamSeriesDownsamplingType;

/**
 * @author Dan Shannon
 *
 */
public class StravaStreamSeriesDownsamplingTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaStreamSeriesDownsamplingType type : StravaStreamSeriesDownsamplingType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaStreamSeriesDownsamplingType type : StravaStreamSeriesDownsamplingType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaStreamSeriesDownsamplingType.create(type.getId()));
		}
	}

}
