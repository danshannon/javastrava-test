package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaSegmentActivityType;

/**
 * @author Dan Shannon
 *
 */
public class StravaSegmentActivityTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaSegmentActivityType type : StravaSegmentActivityType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaSegmentActivityType type : StravaSegmentActivityType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaSegmentActivityType.create(type.getId()));
		}
	}

}
