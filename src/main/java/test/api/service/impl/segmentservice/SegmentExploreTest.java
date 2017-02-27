package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javastrava.api.v3.model.StravaMapPoint;
import javastrava.api.v3.model.StravaSegmentExplorerResponse;
import javastrava.api.v3.model.StravaSegmentExplorerResponseSegment;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import javastrava.api.v3.model.reference.StravaSegmentExplorerActivityType;
import javastrava.api.v3.service.Strava;
import test.api.model.StravaSegmentExplorerResponseTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for
 * {@link Strava#segmentExplore(StravaMapPoint, StravaMapPoint, StravaSegmentExplorerActivityType, StravaClimbCategory, StravaClimbCategory)}
 * methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class SegmentExploreTest {
	/**
	 * <p>
	 * Filter by activity type
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testSegmentExplore_filterByActivityType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = TestUtils.strava().segmentExplore(
					new StravaMapPoint(new Float(-39.4f), new Float(136f)), new StravaMapPoint(new Float(-25f), new Float(154f)),
					StravaSegmentExplorerActivityType.RUNNING, null, null);
			assertNotNull(response);
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	/**
	 * <p>
	 * Filter by maximum category
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testSegmentExplore_filterByMaximumCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = TestUtils.strava().segmentExplore(
					new StravaMapPoint(new Float(-39.4f), new Float(136f)), new StravaMapPoint(new Float(-25f), new Float(154f)),
					null, null, StravaClimbCategory.CATEGORY1);
			assertNotNull(response);
			for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
				assertTrue(segment.getClimbCategory().getValue().intValue() <= StravaClimbCategory.CATEGORY1.getValue().intValue());
			}
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	/**
	 * <p>
	 * Filter by minimum category
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testSegmentExplore_filterByMinimumCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = TestUtils.strava().segmentExplore(
					new StravaMapPoint(new Float(-39.4f), new Float(136f)), new StravaMapPoint(new Float(-25f), new Float(154f)),
					null, StravaClimbCategory.HORS_CATEGORIE, null);
			assertNotNull(response);
			for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
				assertTrue(segment.getClimbCategory().getValue().intValue() >= StravaClimbCategory.HORS_CATEGORIE.getValue()
						.intValue());
			}
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	/**
	 * <p>
	 * Filter by both minimum and maximum category
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testSegmentExplore_filterMaxAndMinCategory() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = TestUtils.strava().segmentExplore(
					new StravaMapPoint(new Float(-39.4f), new Float(136f)), new StravaMapPoint(new Float(-25f), new Float(154f)),
					null, null, null);
			assertNotNull(response);
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

	/**
	 * <p>
	 * No filtering
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testSegmentExplore_normal() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentExplorerResponse response = TestUtils.strava().segmentExplore(
					new StravaMapPoint(new Float(-39.4f), new Float(136f)), new StravaMapPoint(new Float(-25f), new Float(154f)),
					null, null, null);
			assertNotNull(response);
			StravaSegmentExplorerResponseTest.validate(response);
		});
	}

}
