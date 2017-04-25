/**
 *
 */
package test.api.segment.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaMapPoint;
import javastrava.model.StravaSegmentExplorerResponse;
import javastrava.model.StravaSegmentExplorerResponseSegment;
import javastrava.model.reference.StravaClimbCategory;
import javastrava.model.reference.StravaSegmentExplorerActivityType;
import test.api.segment.SegmentExploreTest;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#segmentExploreAsync(String, StravaSegmentExplorerActivityType, StravaClimbCategory, StravaClimbCategory)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class SegmentExploreAsyncTest extends SegmentExploreTest {
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testSegmentExplore_filterByActivityType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api()
					.segmentExploreAsync(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), StravaSegmentExplorerActivityType.RUNNING, null, null).get();
			assertNotNull(response);
			SegmentDataUtils.validateSegmentExplorerResponse(response);
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testSegmentExplore_filterByMaximumCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api()
					.segmentExploreAsync(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, null, StravaClimbCategory.CATEGORY1).get();
			assertNotNull(response);
			for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
				assertTrue(segment.getClimbCategory().getValue() <= StravaClimbCategory.CATEGORY1.getValue());
			}
			SegmentDataUtils.validateSegmentExplorerResponse(response);
		});
	}

	@SuppressWarnings("boxing")
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
			SegmentDataUtils.validateSegmentExplorerResponse(response);
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testSegmentExplore_filterMaxAndMinCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExploreAsync(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, null, null).get();
			assertNotNull(response);
			SegmentDataUtils.validateSegmentExplorerResponse(response);
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testSegmentExplore_normal() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExploreAsync(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, null, null).get();
			assertNotNull(response);
			SegmentDataUtils.validateSegmentExplorerResponse(response);
		});
	}

}
