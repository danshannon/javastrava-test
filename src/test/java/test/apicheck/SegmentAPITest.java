package test.apicheck;

import org.junit.Test;

import javastrava.api.API;
import javastrava.api.SegmentAPI;
import javastrava.model.StravaSegment;
import javastrava.model.StravaSegmentEffort;
import javastrava.model.StravaSegmentLeaderboard;
import javastrava.model.reference.StravaAgeGroup;
import javastrava.model.reference.StravaGender;
import javastrava.model.reference.StravaLeaderboardDateRange;
import javastrava.model.reference.StravaWeightClass;
import retrofit.client.Response;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class SegmentAPITest {
	private static SegmentAPI api() {
		return API.instance(SegmentAPI.class, TestUtils.getValidToken());
	}

	/**
	 * Test for {@link SegmentAPI#getSegmentRaw(Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getSegment() throws Exception {
		final Response response = api().getSegmentRaw(SegmentDataUtils.SEGMENT_VALID_ID);
		ResponseValidator.validate(response, StravaSegment.class, "getSegment"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link SegmentAPI#getSegmentLeaderboardRaw(Integer, StravaGender, StravaAgeGroup, StravaWeightClass, Boolean, Integer, StravaLeaderboardDateRange, Integer, Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getSegmentLeaderboard() throws Exception {
		final Response response = api().getSegmentLeaderboardRaw(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, null, null, null, null, null);
		ResponseValidator.validate(response, StravaSegmentLeaderboard.class, "getSegmentLeaderboard"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link SegmentAPI#listAuthenticatedAthleteStarredSegmentsRaw(Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listAuthenticatedAthleteStarredSegments() throws Exception {
		final Response response = api().listAuthenticatedAthleteStarredSegmentsRaw(null, null);
		ResponseValidator.validate(response, StravaSegment.class, "listAuthenticatedAthleteStarredSegments"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link SegmentAPI#listSegmentEffortsRaw(Integer, Integer, String, String, Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listSegmentEfforts() throws Exception {
		final Response response = api().listSegmentEffortsRaw(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, null);
		ResponseValidator.validate(response, StravaSegmentEffort.class, "listSegmentEfforts"); //$NON-NLS-1$
	}

}
