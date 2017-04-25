package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.model.reference.StravaMeasurementMethod;
import javastrava.model.reference.StravaWeightClass;

/**
 * @author Dan Shannon
 *
 */
public class StravaWeightClassTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaWeightClass weightClass : StravaWeightClass.values()) {
			assertNotNull(weightClass.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaWeightClass weightClass : StravaWeightClass.values()) {
			assertNotNull(weightClass.getId());
			assertEquals(weightClass, StravaWeightClass.create(weightClass.getId()));
		}
	}

	/**
	 * Test returning the measurement method
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetMeasurementMethod() {
		for (final StravaWeightClass weightClass : StravaWeightClass.values()) {
			assertNotNull(weightClass.getMeasurementMethod());
		}
	}

	/**
	 * Test returning the list of weight classes with a particular measurement method
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListByMeasurementMethod() {
		for (final StravaMeasurementMethod method : StravaMeasurementMethod.values()) {
			final List<StravaWeightClass> classes = StravaWeightClass.listByMeasurementMethod(method);
			for (final StravaWeightClass weightClass : classes) {
				assertEquals(method, weightClass.getMeasurementMethod());
			}
		}
	}
}
