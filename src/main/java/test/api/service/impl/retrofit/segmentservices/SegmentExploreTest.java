package test.api.service.impl.retrofit.segmentservices;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaMapPoint;
import javastrava.api.v3.model.StravaSegmentExplorerResponse;
import javastrava.api.v3.model.StravaSegmentExplorerResponseSegment;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import javastrava.api.v3.model.reference.StravaSegmentExplorerActivityType;
import javastrava.api.v3.service.SegmentServices;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;

import org.junit.Test;

import test.api.model.StravaSegmentExplorerResponseTest;
import test.utils.TestUtils;

public class SegmentExploreTest {
	// Test cases
	// 1. Normal
	@Test
	public void testSegmentExplore_normal() {
		final SegmentServices service = service();
		final StravaSegmentExplorerResponse response = service.segmentExplore(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f), null, null, null);
		assertNotNull(response);
		StravaSegmentExplorerResponseTest.validate(response);
	}

	// 2. Filter by activity type
	@Test
	public void testSegmentExplore_filterByActivityType() {
		final SegmentServices service = service();
		final StravaSegmentExplorerResponse response = service.segmentExplore(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f),
				StravaSegmentExplorerActivityType.RUNNING, null, null);
		assertNotNull(response);
		StravaSegmentExplorerResponseTest.validate(response);

	}

	// 3. Filter by minimum category
	@Test
	public void testSegmentExplore_filterByMinimumCategory() {
		final SegmentServices service = service();
		final StravaSegmentExplorerResponse response = service.segmentExplore(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f), null,
				StravaClimbCategory.HORS_CATEGORIE, null);
		assertNotNull(response);
		for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
			assertTrue(segment.getClimbCategory().getValue() >= StravaClimbCategory.HORS_CATEGORIE.getValue());
		}
		StravaSegmentExplorerResponseTest.validate(response);
	}

	// 4. Filter by maximum category
	@Test
	public void testSegmentExplore_filterByMaximumCategory() {
		final SegmentServices service = service();
		final StravaSegmentExplorerResponse response = service.segmentExplore(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f), null, null,
				StravaClimbCategory.CATEGORY1);
		assertNotNull(response);
		for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
			assertTrue(segment.getClimbCategory().getValue() <= StravaClimbCategory.CATEGORY1.getValue());
			;
		}
		StravaSegmentExplorerResponseTest.validate(response);
	}

	// 5. Filter by both minimum and maximum category
	@Test
	public void testSegmentExplore_filterMaxAndMinCategory() {
		final SegmentServices service = service();
		final StravaSegmentExplorerResponse response = service.segmentExplore(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f), null, null, null);
		assertNotNull(response);
		StravaSegmentExplorerResponseTest.validate(response);
	}

	private SegmentServices service() {
		return SegmentServicesImpl.implementation(TestUtils.getValidToken());
	}

}
