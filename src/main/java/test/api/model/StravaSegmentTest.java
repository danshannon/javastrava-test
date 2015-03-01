package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaSegmentActivityType;
import test.utils.BeanTest;

/**
 * @author dshannon
 *
 */
public class StravaSegmentTest extends BeanTest<StravaSegment> {

	@Override
	protected Class<StravaSegment> getClassUnderTest() {
		return StravaSegment.class;
	}

	public static void validateSegment(final StravaSegment segment) {
		assertNotNull(segment);
		validateSegment(segment, segment.getId(), segment.getResourceState());
	}

	/**
	 * @param segment
	 * @param id
	 * @param state
	 */
	public static void validateSegment(final StravaSegment segment, final Integer id, final StravaResourceState state) {
		assertNotNull(segment);
		assertEquals(id, segment.getId());
		assertEquals(state, segment.getResourceState());

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(segment.getActivityType());
			assertFalse(segment.getActivityType() == StravaSegmentActivityType.UNKNOWN);
			assertNotNull(segment.getAthleteCount());
			// Can be null, if the athlete's never done the segment (and it's only returned with starred segments anyway)
			if (segment.getAthletePrEffort() != null) {
				StravaSegmentEffortTest.validateSegmentEffort(segment.getAthletePrEffort(), segment.getAthletePrEffort().getId(), segment.getAthletePrEffort().getResourceState());
			}
			assertNotNull(segment.getAverageGrade());
			// Optional assertNotNull(segment.getCity());
			assertNotNull(segment.getClimbCategory());
			assertFalse(segment.getClimbCategory() == StravaClimbCategory.UNKNOWN);
			// Optional assertNotNull(segment.getCountry());
			assertNotNull(segment.getCreatedAt());
			assertNotNull(segment.getDistance());
			assertNotNull(segment.getEffortCount());
			assertNotNull(segment.getElevationHigh());
			assertNotNull(segment.getElevationLow());
			assertNotNull(segment.getEndLatlng());
			assertNotNull(segment.getHazardous());
			assertNotNull(segment.getMap());
			StravaMapTest.validateMap(segment.getMap(),segment.getMap().getId(),segment.getMap().getResourceState(), null);
			assertNotNull(segment.getMaximumGrade());
			assertNotNull(segment.getName());
			assertNotNull(segment.getPrivateSegment());
			assertNotNull(segment.getStarCount());
			assertNotNull(segment.getStarred());
			assertNotNull(segment.getStartLatlng());
			// Optional assertNotNull(segment.getState());
			assertNotNull(segment.getTotalElevationGain());
			assertNotNull(segment.getUpdatedAt());
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(segment.getActivityType());
			assertFalse(segment.getActivityType() == StravaSegmentActivityType.UNKNOWN);
			assertNull(segment.getAthleteCount());

			// Can be null, if the athlete's never done the segment (and it's only returned with starred segments anyway)
			if (segment.getAthletePrEffort() != null) {
				StravaSegmentEffortTest.validateSegmentEffort(segment.getAthletePrEffort(), segment.getAthletePrEffort().getId(), segment.getAthletePrEffort().getResourceState());
			}
			assertNotNull(segment.getAverageGrade());
			// Optional assertNotNull(segment.getCity());
			assertNotNull(segment.getClimbCategory());
			assertFalse(segment.getClimbCategory() == StravaClimbCategory.UNKNOWN);
			// Optional assertNotNull(segment.getCountry());
			assertNull(segment.getCreatedAt());
			assertNotNull(segment.getDistance());
			assertNull(segment.getEffortCount());
			assertNotNull(segment.getElevationHigh());
			assertNotNull(segment.getElevationLow());
			assertNotNull(segment.getEndLatlng());
			assertNull(segment.getHazardous());
			assertNull(segment.getMap());
			assertNotNull(segment.getMaximumGrade());
			assertNotNull(segment.getName());
			assertNotNull(segment.getPrivateSegment());
			assertNull(segment.getStarCount());
			assertNotNull(segment.getStarred());
			assertNotNull(segment.getStartLatlng());
			// Optional assertNotNull(segment.getState());
			assertNull(segment.getTotalElevationGain());
			assertNull(segment.getUpdatedAt());
		}
		if (state == StravaResourceState.META) {
			assertNull(segment.getActivityType());
			assertNull(segment.getAthleteCount());
			assertNull(segment.getAthletePrEffort());
			assertNull(segment.getAverageGrade());
			assertNull(segment.getCity());
			assertNull(segment.getClimbCategory());
			assertFalse(segment.getClimbCategory() == StravaClimbCategory.UNKNOWN);
			assertNull(segment.getCountry());
			assertNull(segment.getCreatedAt());
			assertNull(segment.getDistance());
			assertNull(segment.getEffortCount());
			assertNull(segment.getElevationHigh());
			assertNull(segment.getElevationLow());
			assertNull(segment.getEndLatlng());
			assertNull(segment.getHazardous());
			assertNull(segment.getMap());
			assertNull(segment.getMaximumGrade());
			assertNull(segment.getName());
			assertNull(segment.getPrivateSegment());
			assertNull(segment.getStarCount());
			assertNull(segment.getStarred());
			assertNull(segment.getStartLatlng());
			assertNull(segment.getState());
			assertNull(segment.getTotalElevationGain());
			assertNull(segment.getUpdatedAt());
		}
		if (state == StravaResourceState.UNKNOWN || state == StravaResourceState.UPDATING) {
			fail("Unexpected segment state " + state + " for segment " + segment);
		}
	}
}
