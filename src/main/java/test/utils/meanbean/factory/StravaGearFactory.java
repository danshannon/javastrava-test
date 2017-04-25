package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.model.StravaGear;
import javastrava.model.reference.StravaGearType;
import javastrava.model.reference.StravaResourceState;
import test.service.standardtests.data.GearDataUtils;

/**
 * Mean bean data factory for Strava Gear
 *
 * @author Dan Shannon
 *
 */
public class StravaGearFactory implements Factory<StravaGear> {

	@Override
	public StravaGear create() {
		return GearDataUtils.testGear(StravaResourceState.DETAILED, StravaGearType.BIKE);
	}

}
