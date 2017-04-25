package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaMeasurementMethod;

/**
 * @author Dan Shannon
 *
 */
public class StravaMeasurementMethodTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaMeasurementMethod type : StravaMeasurementMethod.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaMeasurementMethod type : StravaMeasurementMethod.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaMeasurementMethod.create(type.getId()));
		}
	}

}
