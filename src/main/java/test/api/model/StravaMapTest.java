package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaMap;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaMap}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaMapTest extends BeanTest<StravaMap> {

	/**
	 * @param map
	 *            The map to be validated
	 * @param id
	 *            The expected id of the map
	 * @param state
	 *            The expected resource state of the map
	 * @param activity
	 *            The activitity the map is expected to belong to
	 */
	@SuppressWarnings("boxing")
	public static void validateMap(final StravaMap map, final String id, final StravaResourceState state, final StravaActivity activity) {
		assertNotNull(map);
		assertEquals(id, map.getId());
		assertEquals(state, map.getResourceState());

		if ((activity != null) && (activity.getManual() || activity.getTrainer())) {
			return;
		}

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(map.getPolyline());
			// Optional ßassertNotNull(map.getSummaryPolyline());
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNull(map.getPolyline());
			// Optional assertNotNull(map.getSummaryPolyline());
		}
		if (state == StravaResourceState.META) {
			assertNotNull(map.getPolyline());
			assertNotNull(map.getSummaryPolyline());
		}
		if ((state == StravaResourceState.UNKNOWN) || (state == StravaResourceState.UPDATING)) {
			fail("Unexpected state " + state + " for map " + map); //$NON-NLS-1$ //$NON-NLS-2$
		}

	}

	@Override
	protected Class<StravaMap> getClassUnderTest() {
		return StravaMap.class;
	}
}
