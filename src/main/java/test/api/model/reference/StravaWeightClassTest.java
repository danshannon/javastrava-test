package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.reference.StravaMeasurementMethod;
import javastrava.api.v3.model.reference.StravaWeightClass;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaWeightClassTest {
	@Test
	public void testGetDescription() {
		for (final StravaWeightClass weightClass : StravaWeightClass.values()) {
			assertNotNull(weightClass.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaWeightClass weightClass : StravaWeightClass.values()) {
			assertNotNull(weightClass.getId());
			assertEquals(weightClass, StravaWeightClass.create(weightClass.getId()));
		}
	}

	@Test
	public void testGetMeasurementMethod() {
		for (final StravaWeightClass weightClass : StravaWeightClass.values()) {
			assertNotNull(weightClass.getMeasurementMethod());
		}
	}

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
