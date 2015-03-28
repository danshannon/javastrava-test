package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaSegmentActivityType;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaSegmentActivityTypeTest {
	@Test
	public void testGetDescription() {
		for (final StravaSegmentActivityType type : StravaSegmentActivityType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaSegmentActivityType type : StravaSegmentActivityType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaSegmentActivityType.create(type.getId()));
		}
	}

}
