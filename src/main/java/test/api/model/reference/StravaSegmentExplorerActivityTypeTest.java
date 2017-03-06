package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaSegmentExplorerActivityType;

/**
 * @author Dan Shannon
 *
 */
public class StravaSegmentExplorerActivityTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaSegmentExplorerActivityType type : StravaSegmentExplorerActivityType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaSegmentExplorerActivityType type : StravaSegmentExplorerActivityType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaSegmentExplorerActivityType.create(type.getId()));
		}
	}

}
