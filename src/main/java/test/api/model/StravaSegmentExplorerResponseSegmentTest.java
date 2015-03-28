package test.api.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaSegmentExplorerResponseSegment;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaSegmentExplorerResponseSegmentTest extends BeanTest<StravaSegmentExplorerResponseSegment> {

	public static void validate(final StravaSegmentExplorerResponseSegment segment) {
		assertNotNull(segment);
		assertNotNull(segment.getAvgGrade());
		assertNotNull(segment.getClimbCategory());
		assertFalse(segment.getClimbCategory().equals(StravaClimbCategory.UNKNOWN));
		assertNotNull(segment.getClimbCategoryDesc());
		// assertEquals(segment.getClimbCategoryDesc(),segment.getClimbCategory().getDescription());
		assertNotNull(segment.getDistance());
		assertTrue(segment.getDistance() >= 0);
		assertNotNull(segment.getElevDifference());
		assertTrue(segment.getElevDifference() >= 0);
		assertNotNull(segment.getEndLatlng());
		StravaMapPointTest.validate(segment.getEndLatlng());
		assertNotNull(segment.getId());
		assertNotNull(segment.getName());
		assertNotNull(segment.getPoints());
		assertNotNull(segment.getStartLatlng());
		StravaMapPointTest.validate(segment.getStartLatlng());

	}

	@Override
	protected Class<StravaSegmentExplorerResponseSegment> getClassUnderTest() {
		return StravaSegmentExplorerResponseSegment.class;
	}
}
