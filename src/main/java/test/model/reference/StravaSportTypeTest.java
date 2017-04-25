package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaSportType;

/**
 * @author Dan Shannon
 *
 */
public class StravaSportTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaSportType type : StravaSportType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaSportType type : StravaSportType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaSportType.create(type.getId()));
		}
	}

}
