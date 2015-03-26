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
 * @author dshannon
 *
 */
public class StravaMapTest extends BeanTest<StravaMap> {

	/**
	 * @param map
	 * @param id
	 * @param state
	 */
	public static void validateMap(final StravaMap map, final String id, final StravaResourceState state,
			final StravaActivity activity) {
		assertNotNull(map);
		assertEquals(id, map.getId());
		assertEquals(state, map.getResourceState());

		if (activity != null && (activity.getManual() || activity.getTrainer())) {
			return;
		}

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(map.getPolyline());
			assertNotNull(map.getSummaryPolyline());
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNull(map.getPolyline());
			// Optional assertNotNull(map.getSummaryPolyline());
		}
		if (state == StravaResourceState.META) {
			assertNotNull(map.getPolyline());
			assertNotNull(map.getSummaryPolyline());
		}
		if (state == StravaResourceState.UNKNOWN || state == StravaResourceState.UPDATING) {
			fail("Unexpected state " + state + " for map " + map);
		}

	}

	@Override
	protected Class<StravaMap> getClassUnderTest() {
		return StravaMap.class;
	}
}
