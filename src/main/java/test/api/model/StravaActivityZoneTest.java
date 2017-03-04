package test.api.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javastrava.api.v3.model.StravaActivityZone;
import javastrava.api.v3.model.StravaActivityZoneDistributionBucket;
import javastrava.api.v3.model.reference.StravaActivityZoneType;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityZoneTest extends BeanTest<StravaActivityZone> {

	/**
	 * @param zone
	 *            The zone to validate
	 */
	@SuppressWarnings("boxing")
	public static void validate(final StravaActivityZone zone) {
		assertNotNull(zone);

		// Optional assertNotNull(zone.getCustomZones());
		assertNotNull(zone.getDistributionBuckets());
		for (final StravaActivityZoneDistributionBucket bucket : zone.getDistributionBuckets()) {
			StravaActivityZoneDistributionBucketTest.validate(bucket);
		}
		if (zone.getType() == StravaActivityZoneType.HEARTRATE) {
			if (zone.getMax() != null) {
				assertTrue(zone.getMax() >= 0);
			}
		} else {
			assertNull(zone.getMax());
		}
		if (zone.getPoints() != null) {
			assertTrue(zone.getPoints() >= 0);
		}
		if (zone.getScore() != null) {
			assertTrue(zone.getScore() >= 0);
		}
		assertNotNull(zone.getType());
		assertFalse(zone.getType() == StravaActivityZoneType.UNKNOWN);
		return;
	}

	@Override
	protected Class<StravaActivityZone> getClassUnderTest() {
		return StravaActivityZone.class;
	}

}
