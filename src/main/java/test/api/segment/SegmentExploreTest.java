package test.api.segment;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaMapPoint;
import javastrava.model.StravaSegmentExplorerResponse;
import javastrava.model.StravaSegmentExplorerResponseSegment;
import javastrava.model.reference.StravaClimbCategory;
import javastrava.model.reference.StravaSegmentExplorerActivityType;
import test.api.APITest;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#segmentExplore(String, StravaSegmentExplorerActivityType, StravaClimbCategory, StravaClimbCategory)}
 * </p>
 *
 * @author Dan Shannonb
 *
 */
public class SegmentExploreTest extends APITest<StravaSegmentExplorerResponse> {
	/**
	 * @param point1
	 *            The top-left (or north-west corner) point on the bounded area
	 * @param point2
	 *            The bottom-right (or south-east corner) point on the bounded area
	 * @return The string that describes the bounded area, as used by the Strava API
	 */
	protected static String bounds(final StravaMapPoint point1, final StravaMapPoint point2) {
		return point1.getLatitude() + "," + point1.getLongitude() + "," + point2.getLatitude() + "," //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ point2.getLongitude();

	}

	/**
	 * Filter by activity type
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testSegmentExplore_filterByActivityType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExplore(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), StravaSegmentExplorerActivityType.RUNNING,
					null, null);
			assertNotNull(response);
			SegmentDataUtils.validateSegmentExplorerResponse(response);
		});
	}

	/**
	 * Filter by maximum category
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "boxing", "static-method" })
	@Test
	public void testSegmentExplore_filterByMaximumCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExplore(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, null, StravaClimbCategory.CATEGORY1);
			assertNotNull(response);
			for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
				assertTrue(segment.getClimbCategory().getValue() <= StravaClimbCategory.CATEGORY1.getValue());
			}
			SegmentDataUtils.validateSegmentExplorerResponse(response);
		});
	}

	/**
	 * Filter by minimum category
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testSegmentExplore_filterByMinimumCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExplore(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, StravaClimbCategory.HORS_CATEGORIE,
					null);
			assertNotNull(response);
			for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
				assertTrue(segment.getClimbCategory().getValue() >= StravaClimbCategory.HORS_CATEGORIE.getValue());
			}
			SegmentDataUtils.validateSegmentExplorerResponse(response);
		});
	}

	/**
	 * Filter by both minimum and maximum category
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testSegmentExplore_filterMaxAndMinCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExplore(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, null, null);
			assertNotNull(response);
			SegmentDataUtils.validateSegmentExplorerResponse(response);
		});
	}

	/**
	 * Normal
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testSegmentExplore_normal() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = api().segmentExplore(bounds(new StravaMapPoint(-39.4f, 136f), new StravaMapPoint(-25f, 154f)), null, null, null);
			assertNotNull(response);
			SegmentDataUtils.validateSegmentExplorerResponse(response);
		});
	}

	@Override
	protected void validate(final StravaSegmentExplorerResponse result) throws Exception {
		SegmentDataUtils.validateSegmentExplorerResponse(result);

	}

}
