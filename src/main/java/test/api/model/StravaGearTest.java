package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.model.reference.StravaFrameType;
import javastrava.api.v3.model.reference.StravaGearType;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for StravaGear
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaGearTest extends BeanTest<StravaGear> {

	/**
	 * Validate the given gear
	 * 
	 * @param gear
	 *            The gear to validate
	 */
	public static void validateGear(final StravaGear gear) {
		assertNotNull(gear);
		validateGear(gear, gear.getId(), gear.getResourceState());
	}

	/**
	 * @param gear
	 *            The gear to validate
	 * @param id
	 *            Expected id of the gear
	 * @param resourceState
	 *            Expected resource state of the gear
	 */
	public static void validateGear(final StravaGear gear, final String id, final StravaResourceState resourceState) {
		assertNotNull(gear);
		assertEquals(gear.getId(), id);
		if (resourceState == StravaResourceState.DETAILED) {
			assertEquals(resourceState, gear.getResourceState());
			assertNotNull(gear.getDistance());
			if (gear.getGearType() == StravaGearType.BIKE) {
				assertNotNull(gear.getFrameType());
				assertFalse(gear.getFrameType() == StravaFrameType.UNKNOWN);
			}
			assertNotNull(gear.getBrandName());
			assertNotNull(gear.getDescription());
			assertNotNull(gear.getName());
			assertNotNull(gear.getPrimary());
			assertNotNull(gear.getModelName());
		}
		if (resourceState == StravaResourceState.SUMMARY) {
			assertEquals(resourceState, gear.getResourceState());
			assertNotNull(gear.getDistance());
			assertNull(gear.getBrandName());
			assertNull(gear.getDescription());
			assertNull(gear.getFrameType());
			assertNull(gear.getModelName());
			assertNotNull(gear.getName());
			assertNotNull(gear.getPrimary());
		}
		if (resourceState == StravaResourceState.META) {
			assertEquals(resourceState, gear.getResourceState());
			assertNull(gear.getDistance());
			assertNull(gear.getBrandName());
			assertNull(gear.getDescription());
			assertNull(gear.getFrameType());
			assertNull(gear.getModelName());
			assertNull(gear.getName());
			assertNull(gear.getPrimary());
		}
		if ((resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			fail("Unexpected resource state " + resourceState); //$NON-NLS-1$
		}
	}

	@Override
	protected Class<StravaGear> getClassUnderTest() {
		return StravaGear.class;
	}

}
