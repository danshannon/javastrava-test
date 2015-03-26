package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;

import org.junit.Test;

/**
 * @author dshannon
 *
 */
public class StravaStreamSeriesDownsamplingTypeTest {
	@Test
	public void testGetDescription() {
		for (final StravaStreamSeriesDownsamplingType type : StravaStreamSeriesDownsamplingType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaStreamSeriesDownsamplingType type : StravaStreamSeriesDownsamplingType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaStreamSeriesDownsamplingType.create(type.getId()));
		}
	}

}
