package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;

import org.junit.Test;

/**
 * @author dshannon
 *
 */
public class StravaStreamResolutionTypeTest {
	@Test
	public void testGetDescription() {
		for (final StravaStreamResolutionType type : StravaStreamResolutionType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaStreamResolutionType type : StravaStreamResolutionType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaStreamResolutionType.create(type.getId()));
		}
	}

}
