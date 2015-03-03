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

		if (state == StravaResourceState.DETAILED || state == StravaResourceState.SUMMARY) {
			assertNotNull(segment.getActivityType());
			assertFalse("Segment " + segment.getId() + " has an unknown activity type",segment.getActivityType() == StravaSegmentActivityType.UNKNOWN);
			assertNull(segment.getAthleteCount());

			// Can be null, if the athlete's never done the segment (and it's only returned with starred segments anyway)
			if (segment.getAthletePrEffort() != null) {
				StravaSegmentEffortTest.validateSegmentEffort(segment.getAthletePrEffort(), segment.getAthletePrEffort().getId(),
						segment.getAthletePrEffort().getResourceState());
			}

			assertNotNull(segment.getAverageGrade());

			// Optional assertNotNull(segment.getCity());

			assertNotNull(segment.getClimbCategory());
			assertFalse(segment.getClimbCategory() == StravaClimbCategory.UNKNOWN);

			// Optional assertNotNull(segment.getCountry());

			assertNotNull(segment.getDistance());
			assertNotNull(segment.getElevationHigh());
			assertNotNull(segment.getElevationLow());
			assertNotNull(segment.getEndLatlng());
			assertNotNull(segment.getMaximumGrade());
			
			// Optional assertNotNull("Segment " + segment.getId() + " has no name!", segment.getName());
			
			assertNotNull(segment.getPrivateSegment());
			assertNotNull(segment.getStarred());
			assertNotNull(segment.getStartLatlng());
			// Optional assertNotNull(segment.getState());
		}

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(segment.getCreatedAt());
			assertNotNull(segment.getEffortCount());
			assertNotNull(segment.getHazardous());
			assertNotNull(segment.getMap());
			StravaMapTest.validateMap(segment.getMap(), segment.getMap().getId(), segment.getMap().getResourceState(), null);
			assertNotNull(segment.getStarCount());
			assertNotNull(segment.getTotalElevationGain());
			assertNotNull(segment.getUpdatedAt());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNull(segment.getCreatedAt());
			assertNull(segment.getEffortCount());
			assertNull(segment.getHazardous());
			assertNull(segment.getMap());
			assertNull(segment.getStarCount());
			assertNull(segment.getTotalElevationGain());
			assertNull(segment.getUpdatedAt());
			return;
		}
		if (state == StravaResourceState.META) {
			assertNull(segment.getActivityType());
			assertNull(segment.getAthleteCount());
			assertNull(segment.getAthletePrEffort());
			assertNull(segment.getAverageGrade());
			assertNull(segment.getCity());
			assertNull(segment.getClimbCategory());
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
			return;
		}
		fail("Unexpected segment state " + state + " for segment " + segment);
	}
}
