package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaActivityType;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityTypeTest {
	/**
	 * @see test.api.model.reference.StravaReferenceTypeTest#testGetId()
	 */
	@Test
	public void testGetId() {
		for (final StravaActivityType type : StravaActivityType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaActivityType.create(type.getId()));
		}
	}

	/**
	 * @see test.api.model.reference.StravaReferenceTypeTest#testGetValue()
	 */
	public void testGetValue() {
		for (final StravaActivityType type : StravaActivityType.values()) {
			assertNotNull(type.getValue());
			assertEquals(type, StravaActivityType.create(type.getValue()));
		}

	}

	/**
	 * @see test.api.model.reference.StravaReferenceTypeTest#testCreate()
	 */
	public void testCreate() {
		for (final StravaActivityType type : StravaActivityType.values()) {
			assertEquals(type, StravaActivityType.create(type.getValue()));
		}

	}

}
