package test.service.standardtests.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.model.reference.StravaGearType;
import javastrava.api.v3.model.reference.StravaResourceState;
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
}
