package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivityZone;
import javastrava.api.v3.model.StravaActivityZoneDistributionBucket;
import javastrava.api.v3.model.reference.StravaActivityZoneType;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityZoneTest extends BeanTest<StravaActivityZone> {

	public static void validateActivityZone(final StravaActivityZone zone, final StravaResourceState state) {
		assertNotNull(zone);
		assertEquals(state, zone.getResourceState());

		if (state == StravaResourceState.DETAILED) {
			// Optional assertNotNull(zone.getCustomZones());
			assertNotNull(zone.getDistributionBuckets());
			for (final StravaActivityZoneDistributionBucket bucket : zone.getDistributionBuckets()) {
				StravaActivityZoneDistributionBucketTest.validateBucket(bucket);
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
		if (state == StravaResourceState.SUMMARY) {
			// Optional assertNotNull(zone.getCustomZones());
			assertNotNull(zone.getDistributionBuckets());
			for (final StravaActivityZoneDistributionBucket bucket : zone.getDistributionBuckets()) {
				StravaActivityZoneDistributionBucketTest.validateBucket(bucket);
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
		if (state == StravaResourceState.META) {
			assertNull(zone.getCustomZones());
			assertNull(zone.getDistributionBuckets());
			assertNull(zone.getMax());
			assertNull(zone.getPoints());
			assertNull(zone.getScore());
			assertNull(zone.getSensorBased());
			assertNull(zone.getType());
			return;
		}
		fail("unexpected state " + state + " for activity zone " + zone);
	}

	@Override
	protected Class<StravaActivityZone> getClassUnderTest() {
		return StravaActivityZone.class;
	}
}
