package test.api.rest.segment;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaMapPoint;
import javastrava.api.v3.model.StravaSegmentExplorerResponse;
import javastrava.api.v3.model.StravaSegmentExplorerResponseSegment;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import javastrava.api.v3.model.reference.StravaSegmentExplorerActivityType;

import org.junit.Test;

import test.api.model.StravaSegmentExplorerResponseTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;

public class SegmentExploreTest extends APITest {
	// 2. Filter by activity type
	@Test
	public void testSegmentExplore_filterByActivityType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExplore(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)),
					StravaSegmentExplorerActivityType.RUNNING, null, null);
			assertNotNull(response);
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	// 4. Filter by maximum category
	@Test
	public void testSegmentExplore_filterByMaximumCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExplore(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null,
					null, StravaClimbCategory.CATEGORY1);
			assertNotNull(response);
			for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
				assertTrue(segment.getClimbCategory().getValue() <= StravaClimbCategory.CATEGORY1.getValue());
				;
			}
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	// 3. Filter by minimum category
	@Test
	public void testSegmentExplore_filterByMinimumCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExplore(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null,
					StravaClimbCategory.HORS_CATEGORIE, null);
			assertNotNull(response);
			for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
				assertTrue(segment.getClimbCategory().getValue() >= StravaClimbCategory.HORS_CATEGORIE.getValue());
			}
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	// 5. Filter by both minimum and maximum category
	@Test
	public void testSegmentExplore_filterMaxAndMinCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExplore(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null,
					null, null);
			assertNotNull(response);
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	// Test cases
	// 1. Normal
	@Test
	public void testSegmentExplore_normal() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExplore(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null,
					null, null);
			assertNotNull(response);
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	private static String bounds(final StravaMapPoint point1, final StravaMapPoint point2) {
		return point1.getLatitude() + "," + point1.getLongitude() + "," + point2.getLatitude() + "," //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ point2.getLongitude();

	}

}
