package test.api.service.impl.retrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.SegmentEffortServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.SegmentEffortServicesImpl;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * Unit tests for {@link SegmentEffortServicesImpl}
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class SegmentEffortServicesImplTest {
	private SegmentEffortServices segmentEffortService;

	/**
	 * <p>
	 * Test we get a {@link SegmentEffortServicesImpl service implementation} successfully with a valid token
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Test
	public void testImplementation_validToken() {
		SegmentEffortServices service = SegmentEffortServicesImpl.implementation(TestUtils.getValidToken());
		assertNotNull("Got a NULL service for a valid token", service);
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServicesImpl service implementation} if the token isn't valid
	 * </p>
	 */
	@Test
	public void testImplementation_invalidToken() {
		SegmentEffortServices service = null;
		try {
			service = SegmentEffortServicesImpl.implementation(TestUtils.INVALID_TOKEN);
			service.getSegmentEffort(TestUtils.SEGMENT_EFFORT_VALID_ID);
		} catch (UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Got a working service for an invalid token!");
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServicesImpl service implementation} if the token has been revoked by the user
	 * </p>
	 */
	@Test
	public void testImplementation_revokedToken() {
		SegmentEffortServices service = SegmentEffortServicesImpl.implementation(getRevokedToken());
		try {
			service.getSegmentEffort(TestUtils.SEGMENT_EFFORT_VALID_ID);
		} catch (UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Revoked a token, but it's still useful");
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServicesImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the
	 * caching strategy is working)
	 * </p>
	 */
	@Test
	public void testImplementation_implementationIsCached() {
		SegmentEffortServices service = SegmentEffortServicesImpl.implementation(TestUtils.getValidToken());
		SegmentEffortServices service2 = SegmentEffortServicesImpl.implementation(TestUtils.getValidToken());
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServicesImpl service implementation} for a second, valid, different token, we get a DIFFERENT
	 * implementation
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		SegmentEffortServices service = getService();
		SegmentEffortServices service2 = getServiceWithoutWriteAccess();
		assertFalse(service == service2);
	}

	// Test cases
	// 1. Valid effort
	// 2. Invalid effort
	// 3. Private effort which does belong to the current athlete (is returned)
	// 4. Private effort which doesn't belong to the current athlete (is not returned)
	@Test
	public void testGetSegmentEffort_valid() {
		SegmentEffortServices service = getService();
		Long id = TestUtils.SEGMENT_EFFORT_VALID_ID;
		StravaSegmentEffort effort = service.getSegmentEffort(id);
		assertNotNull(effort);
		assertEquals(id, effort.getId());
	}

	@Test
	public void testGetSegmentEffort_invalid() {
		SegmentEffortServices service = getService();
		Long id = TestUtils.SEGMENT_EFFORT_INVALID_ID;
		StravaSegmentEffort effort = service.getSegmentEffort(id);
		assertNull(effort);
	}

	@Test
	public void testGetSegmentEffort_private() {
		SegmentEffortServices service = getService();
		Long id = TestUtils.SEGMENT_EFFORT_PRIVATE_ID;
		StravaSegmentEffort effort = service.getSegmentEffort(id);
		assertNotNull(effort);
		assertEquals(id, effort.getId());
	}

	@Test
	public void testGetSegmentEffort_privateOtherAthlete() {
		SegmentEffortServices service = getService();
		Long id = TestUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID;
		StravaSegmentEffort effort = service.getSegmentEffort(id);
		assertNotNull(effort);
		StravaSegmentEffort comparison = new StravaSegmentEffort();
		comparison.setId(id);
		assertEquals(comparison, effort);
	}

	private SegmentEffortServices getService() {
		if (this.segmentEffortService == null) {
			this.segmentEffortService = SegmentEffortServicesImpl.implementation(TestUtils.getValidToken());
		}
		return this.segmentEffortService;
	}

	private String getRevokedToken() {
		this.segmentEffortService = null;
		return TestUtils.getRevokedToken();
	}

	private SegmentEffortServices getServiceWithoutWriteAccess() {
		this.segmentEffortService = null;
		return SegmentEffortServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
	}

	/**
	 * @param effort
	 * @param id
	 * @param state
	 */
	public static void validateSegmentEffort(StravaSegmentEffort effort, Long id, StravaResourceState state) {
		assertNotNull(effort);
		assertEquals(id,effort.getId());
		assertEquals(state,effort.getResourceState());
		
		if (state == StravaResourceState.DETAILED) {
			assertNotNull(effort.getActivity());
			ActivityServicesImplTest.validateActivity(effort.getActivity(), effort.getActivity().getId(), effort.getActivity().getResourceState());
			assertNotNull(effort.getAthlete());
			AthleteServicesImplTest.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(), effort.getAthlete().getResourceState());
			if (effort.getActivity().getAthlete() != null) {
				assertEquals(effort.getActivity().getAthlete().getId(),effort.getAthlete().getId());
			}
			
			// Only returned for rides, and then only if it was measured
			if (effort.getActivity().getType() == StravaActivityType.RIDE) {
				if (effort.getAverageCadence() != null) {
					assertTrue(effort.getAverageCadence() >=0);
				}
				if (effort.getAverageWatts() != null) {
					assertTrue(effort.getAverageWatts() >= 0);
				}
			} else {
				// If we can't tell what sort of activity it was, then can't tell if average cadence/watts can be set or not
				if (effort.getActivity().getResourceState() != StravaResourceState.META) {
					assertNull(effort.getAverageCadence());
					assertNull(effort.getAverageWatts());
				}
			}
			
			// If returned then there should be a max heartrate too
			if (effort.getAverageHeartrate() != null) {
				assertTrue(effort.getAverageHeartrate() >= 0);
				assertTrue(effort.getMaxHeartrate() >= 0);
				assertTrue(effort.getMaxHeartrate() >= effort.getAverageHeartrate());
			}
			// If there's a max heartrate, then there should be an average
			if (effort.getMaxHeartrate() != null) {
				assertNotNull(effort.getAverageHeartrate());
			}
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			assertNotNull(effort.getEndIndex());
			assertNotNull(effort.getHidden());
			// Only returned for starred segments assertNotNull(effort.getIsKom());
			
			// Only returned if in the top 10 at the time
			if (effort.getKomRank() != null) {
				assertTrue(effort.getKomRank() <= 10);
				assertTrue(effort.getKomRank() >= 1);
			}
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			// Only returned if it's one of the top 3 efforts for the athlete
			if (effort.getPrRank() != null) {
				assertTrue(effort.getPrRank() > 0);
				assertTrue(effort.getPrRank() < 4);
			}
			assertNotNull(effort.getSegment());
			SegmentServicesImplTest.validateSegment(effort.getSegment(), effort.getSegment().getId(), effort.getSegment().getResourceState());
			assertNotNull(effort.getStartDate());
			assertNotNull(effort.getStartDateLocal());
			assertNotNull(effort.getStartIndex());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(effort.getActivity());
			ActivityServicesImplTest.validateActivity(effort.getActivity(), effort.getActivity().getId(), effort.getActivity().getResourceState());
			assertNotNull(effort.getAthlete());
			AthleteServicesImplTest.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(), effort.getAthlete().getResourceState());
			if (effort.getActivity().getAthlete() != null) {
				assertEquals(effort.getActivity().getAthlete().getId(),effort.getAthlete().getId());
			}
			// Only returned for rides, and then only if it was measured
			if (effort.getActivity().getType() == StravaActivityType.RIDE) {
				if (effort.getAverageCadence() != null) {
					assertTrue(effort.getAverageCadence() >=0);
				}
				if (effort.getAverageWatts() != null) {
					assertTrue(effort.getAverageWatts() >= 0);
				}
			} else {
				// If we can't tell what sort of activity it was, then can't tell if average cadence/watts can be set or not
				if (effort.getActivity().getResourceState() != StravaResourceState.META) {
					assertNull(effort.getAverageCadence());
					assertNull(effort.getAverageWatts());
				}
			}
			
			// If returned then there should be a max heartrate too
			if (effort.getAverageHeartrate() != null) {
				assertTrue(effort.getAverageHeartrate() >= 0);
				assertTrue(effort.getMaxHeartrate() >= 0);
				assertTrue(effort.getMaxHeartrate() >= effort.getAverageHeartrate());
			}
			// If there's a max heartrate, then there should be an average
			if (effort.getMaxHeartrate() != null) {
				assertNotNull(effort.getAverageHeartrate());
			}

			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			assertNotNull(effort.getEndIndex());
			assertNotNull(effort.getHidden());
			// Only returned for starred segments 
			// assertNotNull(effort.getIsKom());
			//
			if (effort.getKomRank() != null) {
				assertTrue(effort.getKomRank() <= 10);
				assertTrue(effort.getKomRank() >= 1);
			}

			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			// Only returned if it's one of the top 3 efforts for the athlete
			if (effort.getPrRank() != null) {
				assertTrue(effort.getPrRank() > 0);
				assertTrue(effort.getPrRank() < 4);
			}
			assertNotNull(effort.getSegment());
			SegmentServicesImplTest.validateSegment(effort.getSegment(), effort.getSegment().getId(), effort.getSegment().getResourceState());
			assertNotNull(effort.getStartDate());
			assertNotNull(effort.getStartDateLocal());
			assertNotNull(effort.getStartIndex());
			return;
		}
		if (state == StravaResourceState.META) {
			assertNull(effort.getActivity());
			assertNull(effort.getAthlete());
			assertNull(effort.getAverageCadence());
			assertNull(effort.getAverageHeartrate());
			assertNull(effort.getAverageWatts());
			assertNull(effort.getDistance());
			assertNull(effort.getElapsedTime());
			assertNull(effort.getEndIndex());
			assertNull(effort.getHidden());
			assertNull(effort.getIsKom());
			assertNull(effort.getKomRank());
			assertNull(effort.getMaxHeartrate());
			assertNull(effort.getMovingTime());
			assertNull(effort.getName());
			assertNull(effort.getPrRank());
			assertNull(effort.getSegment());
			assertNull(effort.getStartDate());
			assertNull(effort.getStartDateLocal());
			assertNull(effort.getStartIndex());
			return;
		}
		fail("Unexpected state for segment effort " + state + " " + effort);
	}
}
