package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaSegmentActivityType;

/**
 * <p>
 * Meanbean requires a factory to create test objects of type {@link StravaSegment}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentFactory implements Factory<StravaSegment> {

	@SuppressWarnings("boxing")
	@Override
	public StravaSegment create() {
		final StravaSegment segment = new StravaSegment();
		segment.setActivityType(StravaSegmentActivityType.RIDE);
		segment.setAthleteCount(1);
		segment.setAthletePrEffort(null);
		segment.setAverageGrade(1F);
		segment.setCity("Melbourne"); //$NON-NLS-1$
		segment.setClimbCategory(StravaClimbCategory.CATEGORY4);
		segment.setCountry("Australia"); //$NON-NLS-1$
		segment.setCreatedAt(null);
		segment.setDistance(1000F);
		segment.setEffortCount(1);
		segment.setElevationHigh(1000F);
		segment.setElevationLow(1F);
		segment.setEndLatlng(null);
		segment.setHazardous(Boolean.FALSE);
		segment.setId(1);
		segment.setMap(null);
		segment.setMaximumGrade(10f);
		segment.setName("A name by any other rose"); //$NON-NLS-1$
		segment.setPrivateSegment(Boolean.FALSE);
		segment.setResourceState(StravaResourceState.DETAILED);
		segment.setStarCount(0);
		segment.setStarred(Boolean.FALSE);
		segment.setStartLatlng(null);
		segment.setState("VIC"); //$NON-NLS-1$
		segment.setTotalElevationGain(1001F);
		segment.setUpdatedAt(null);
		return segment;
	}

}
