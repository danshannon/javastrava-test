package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.model.StravaGear;
import javastrava.model.reference.StravaFrameType;
import javastrava.model.reference.StravaGearType;
import javastrava.model.reference.StravaResourceState;
import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for Gear
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GearDataUtils {
	private static Random random = new Random();

	private static TextProducer fairy = Fairy.create().textProducer();

	/**
	 * Identifier of gear that belongs to the authenticated user
	 */
	public static String	GEAR_VALID_ID;
	/**
	 * Invalid gear identifier
	 */
	public static String	GEAR_INVALID_ID;
	/**
	 * Identifier of gear that belongs to someone other than the authenticated athlete
	 */
	public static String	GEAR_OTHER_ATHLETE_ID;

	static {
		GEAR_VALID_ID = TestUtils.stringProperty("test.gearId"); //$NON-NLS-1$
		GEAR_INVALID_ID = TestUtils.stringProperty("test.gearInvalidId"); //$NON-NLS-1$
		GEAR_OTHER_ATHLETE_ID = TestUtils.stringProperty("test.gearOtherAthleteId"); //$NON-NLS-1$

	}

	/**
	 * @param resourceState
	 *            The resource state the gear should be created in
	 * @param gearType
	 *            Gear type to create
	 * @return A bike, as random as possible
	 */
	@SuppressWarnings("boxing")
	public static StravaGear testGear(StravaResourceState resourceState, StravaGearType gearType) {
		if ((gearType == StravaGearType.UNKNOWN) || (resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			throw new IllegalArgumentException();
		}

		final StravaGear gear = new StravaGear();

		// META / PRIVATE attributes are only the id and resource state
		if (gearType == StravaGearType.BIKE) {
			gear.setId("b" + random.nextInt(Integer.MAX_VALUE)); //$NON-NLS-1$
		} else {
			gear.setId("s" + random.nextInt(Integer.MAX_VALUE)); //$NON-NLS-1$
		}
		gear.setResourceState(resourceState);

		// SUMMARY attributes
		if ((resourceState == StravaResourceState.SUMMARY) || (resourceState == StravaResourceState.DETAILED)) {
			gear.setName(fairy.word(2));
			gear.setPrimary(random.nextBoolean());
			gear.setDistance(random.nextFloat() * 100000);
		}

		// DETAILED attributes
		if (resourceState == StravaResourceState.DETAILED) {
			gear.setBrandName(fairy.word());
			gear.setDescription(fairy.sentence());
			gear.setFrameType(RefDataUtils.randomFrameType());
			gear.setGearType(gearType);
			gear.setModelName(fairy.word(2));
		}

		// Return the resulting gear
		return gear;
	}

	/**
	 * @param resourceState
	 *            The resource state the gear should be created in
	 * @param gearType
	 *            Type of gear to create
	 * @return A list of gear with up to 10,000 entries
	 */
	public static List<StravaGear> testGearList(StravaResourceState resourceState, StravaGearType gearType) {
		final ArrayList<StravaGear> list = new ArrayList<>();
		final int entries = random.nextInt(9999);
		for (int i = 0; i < entries; i++) {
			list.add(testGear(resourceState, gearType));
		}
		return list;
	}

	/**
	 * Validate the given gear
	 * 
	 * @param gear
	 *            The gear to validate
	 */
	public static void validateGear(final StravaGear gear) {
		assertNotNull(gear);
		GearDataUtils.validateGear(gear, gear.getId(), gear.getResourceState());
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
}
