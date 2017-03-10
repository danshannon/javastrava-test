/**
 *
 */
package test.api.rest.segment.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaMapPoint;
import javastrava.api.v3.model.StravaSegmentExplorerResponse;
import javastrava.api.v3.model.StravaSegmentExplorerResponseSegment;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import javastrava.api.v3.model.reference.StravaSegmentExplorerActivityType;

import org.junit.Test;

import test.api.model.StravaSegmentExplorerResponseTest;
import test.api.rest.segment.SegmentExploreTest;
import test.utils.RateLimitedTestRunner;

/**
 * @author danshannon
 *
 */
public class SegmentExploreAsyncTest extends SegmentExploreTest {
	// 2. Filter by activity type
	@Override
	@Test
	public void testSegmentExplore_filterByActivityType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api()
					.segmentExploreAsync(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), StravaSegmentExplorerActivityType.RUNNING, null, null).get();
			assertNotNull(response);
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	// 4. Filter by maximum category
	@Override
	@Test
	public void testSegmentExplore_filterByMaximumCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api()
					.segmentExploreAsync(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, null, StravaClimbCategory.CATEGORY1).get();
			assertNotNull(response);
			for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
				assertTrue(segment.getClimbCategory().getValue() <= StravaClimbCategory.CATEGORY1.getValue());
				;
			}
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	// 3. Filter by minimum category
	@Override
	@Test
	public void testSegmentExplore_filterByMinimumCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api()
					.segmentExploreAsync(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, StravaClimbCategory.HORS_CATEGORIE, null).get();
			assertNotNull(response);
			for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
				assertTrue(segment.getClimbCategory().getValue() >= StravaClimbCategory.HORS_CATEGORIE.getValue());
			}
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	// 5. Filter by both minimum and maximum category
	@Override
	@Test
	public void testSegmentExplore_filterMaxAndMinCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExploreAsync(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, null, null).get();
			assertNotNull(response);
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	// Test cases
	// 1. Normal
	@Override
	@Test
	public void testSegmentExplore_normal() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExploreAsync(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, null, null).get();
			assertNotNull(response);
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

}
