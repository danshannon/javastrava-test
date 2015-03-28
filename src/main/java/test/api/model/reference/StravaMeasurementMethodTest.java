package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaMeasurementMethod;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaMeasurementMethodTest {
	@Test
	public void testGetDescription() {
		for (final StravaMeasurementMethod type : StravaMeasurementMethod.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaMeasurementMethod type : StravaMeasurementMethod.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaMeasurementMethod.create(type.getId()));
		}
	}

}
