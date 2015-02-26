/**
 * 
 */
package test.api.service.impl.retrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaStatistics;
import javastrava.api.v3.model.reference.StravaFollowerState;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaMeasurementMethod;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.AthleteServices;
import javastrava.api.v3.service.Strava;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.AthleteServicesImpl;
import javastrava.util.Paging;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * Unit tests for {@link AthleteServicesImpl}
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class AthleteServicesImplTest {
	@Test
	public void testImplementation_validToken() {
		AthleteServices services = AthleteServicesImpl.implementation(TestUtils.getValidToken());
		assertNotNull(services);
	}

	@Test
	public void testImplementation_invalidToken() {
		AthleteServices services = AthleteServicesImpl.implementation(TestUtils.INVALID_TOKEN);
		try {
			services.getAuthenticatedAthlete();
		} catch (UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Got a service object using an invalid token");
	}

	@Test
	public void testImplementation_revokedToken() {
		AthleteServices services = AthleteServicesImpl.implementation(TestUtils.getRevokedToken());
		try {
			services.getAuthenticatedAthlete();
		} catch (UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Got a service object using a revoked token");

	}

	@Test
	public void testImplementation_implementationIsCached() {
		String token = TestUtils.getValidToken();
		AthleteServices service1 = AthleteServicesImpl.implementation(token);
		AthleteServices service2 = AthleteServicesImpl.implementation(token);
		assertTrue(service1 == service2);
	}

	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		String token1 = TestUtils.getValidToken();
		AthleteServices service1 = AthleteServicesImpl.implementation(token1);

		String token2 = TestUtils.getValidTokenWithoutWriteAccess();
		assertFalse(token1.equals(token2));
		AthleteServices service2 = AthleteServicesImpl.implementation(token2);
		assertFalse(service1 == service2);
	}

	@Test
	public void testGetAuthenticatedAthlete() {
		AthleteServices service = getService();
		StravaAthlete athlete = service.getAuthenticatedAthlete();
		validateAthlete(athlete,TestUtils.ATHLETE_AUTHENTICATED_ID,StravaResourceState.DETAILED);

	}

	@Test
	public void testGetAthlete_validAthlete() {
		AthleteServices service = getService();
		StravaAthlete athlete = service.getAthlete(TestUtils.ATHLETE_VALID_ID);
		validateAthlete(athlete,TestUtils.ATHLETE_VALID_ID,StravaResourceState.SUMMARY);
	}

	@Test
	public void testGetAthlete_invalidAthlete() {
		AthleteServices service = getService();
		StravaAthlete athlete = service.getAthlete(TestUtils.ATHLETE_INVALID_ID);
		assertNull(athlete);

	}

	@Test
	public void testGetAthlete_privateAthlete() {
		AthleteServices service = getService();
		StravaAthlete athlete = service.getAthlete(TestUtils.ATHLETE_PRIVATE_ID);
		assertNotNull(athlete);
		assertEquals(TestUtils.ATHLETE_PRIVATE_ID, athlete.getId());
		validateAthlete(athlete,TestUtils.ATHLETE_PRIVATE_ID,StravaResourceState.SUMMARY);
	}

	@Test
	public void testUpdateAuthenticatedAthlete() {
		AthleteServices service = getService();
		StravaAthlete athlete = service.getAuthenticatedAthlete();

		String city = athlete.getCity();
		String state = athlete.getState();
		StravaGender sex = athlete.getSex();
		String country = athlete.getCountry();
		athlete.setWeight(92.0f);
		StravaAthlete returnedAthlete = service.updateAuthenticatedAthlete(null, null, null, null, new Float(92));
		validateAthlete(returnedAthlete,athlete.getId(),StravaResourceState.DETAILED);
		returnedAthlete = service.updateAuthenticatedAthlete(city, state, country, sex, null);
		validateAthlete(returnedAthlete,athlete.getId(),StravaResourceState.DETAILED);
	}

	@Test
	public void testUpdateAuthenticatedAthlete_noWriteAccess() {
		AthleteServices service = getServiceWithoutWriteAccess();
		@SuppressWarnings("unused")
		StravaAthlete athlete = service.getAuthenticatedAthlete();

		try {
			athlete = service.updateAuthenticatedAthlete(null, null, null, null, new Float(92));
		} catch (UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Succesfully updated authenticated athlete despite not having write access");

	}

	// Test cases
	// 1. Valid athlete with some KOM's
	@Test
	public void testListAthleteKOMs_withKOM() {
		AthleteServices service = getService();
		List<StravaSegmentEffort> koms = service.listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
		assertNotNull(koms);
		assertFalse(koms.size() == 0);
	}

	// 2. Valid athlete with no KOM's
	@Test
	public void testListAthleteKOMs_withoutKOM() {
		AthleteServices service = getService();
		List<StravaSegmentEffort> koms = service.listAthleteKOMs(TestUtils.ATHLETE_WITHOUT_KOMS);
		assertNotNull(koms);
		assertTrue(koms.size() == 0);
	}

	// 2. Invalid athlete
	@Test
	public void testListAthleteKOMs_invalidAthlete() {
		AthleteServices service = getService();
		List<StravaSegmentEffort> koms = null;
		koms = service.listAthleteKOMs(TestUtils.ATHLETE_INVALID_ID);

		assertNull(koms);
	}

	// 3. Paging - size only
	@Test
	public void testListAthleteKOMs_pageSize() {
		AthleteServices service = getService();
		List<StravaSegmentEffort> koms = service.listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, new Paging(1, 1));
		assertEquals(1, koms.size());
	}

	// 4. Paging - size and page
	@Test
	public void testListAthleteKOMs_pageSizeAndNumber() {
		AthleteServices service = getService();
		List<StravaSegmentEffort> koms = service.listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, new Paging(2, 1));
		Long effortId = koms.get(0).getId();
		assertEquals(1, koms.size());
		koms = service.listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, new Paging(1, 2));
		assertEquals(2, koms.size());
		assertEquals(effortId, koms.get(1).getId());
	}

	// 5. Paging - out of range high
	@Test
	public void testListAthleteKOMs_pagingOutOfRangeHigh() {
		AthleteServices service = getService();
		List<StravaSegmentEffort> koms = service.listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, new Paging(1000, 200));
		assertEquals(0, koms.size());
	}

	// 6. Paging - out of range low
	@Test
	public void testListAthleteKOMs_pagingOutOfRangeLow() {
		AthleteServices service = getService();
		try {
			@SuppressWarnings("unused")
			List<StravaSegmentEffort> koms = service.listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, new Paging(-1, -1));
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}
		fail("Illegal paging parameters were accepted");
	}

	@Test
	public void testListAthleteKOMs_pagingIgnoreFirstN() {
		List<StravaSegmentEffort> efforts = getService().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, new Paging(1, 2, 1, 0));
		assertNotNull(efforts);
		assertEquals(1, efforts.size());
		List<StravaSegmentEffort> expectedEfforts = getService().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
		assertEquals(expectedEfforts.get(1), efforts.get(0));
	}

	@Test
	public void testListAthleteKOMs_pagingIgnoreLastN() {
		List<StravaSegmentEffort> efforts = getService().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, new Paging(1, 2, 0, 1));
		assertNotNull(efforts);
		assertEquals(1, efforts.size());
		List<StravaSegmentEffort> expectedEfforts = getService().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
		assertEquals(expectedEfforts.get(0), efforts.get(0));
	}

	@Test
	public void testListAuthenticatedAthleteFriends_friends() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAuthenticatedAthleteFriends();
		assertNotNull(friends);
		assertFalse(friends.isEmpty());
		for (StravaAthlete athlete : friends) {
			validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
		}
	}

	@Test
	public void testListAuthenticatedAthleteFriends_pageSize() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAuthenticatedAthleteFriends(new Paging(1, 1));
		assertNotNull(friends);
		assertEquals(1, friends.size());
	}

	@Test
	public void testListAuthenticatedAthleteFriends_pageSizeAndNumber() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAuthenticatedAthleteFriends(new Paging(2, 1));
		assertNotNull(friends);
		assertEquals(1, friends.size());
		Integer friendId = friends.get(0).getId();
		friends = service.listAuthenticatedAthleteFriends(new Paging(1, 2));
		assertNotNull(friends);
		assertEquals(2, friends.size());
		assertEquals(friendId, friends.get(1).getId());
	}

	@Test
	public void testListAuthenticatedAthleteFriends_pagingOutOfRangeHigh() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAuthenticatedAthleteFriends(new Paging(1000, 200));
		assertNotNull(friends);
		assertEquals(0, friends.size());
	}

	@Test
	public void testListAuthenticatedAthleteFriends_pagingOutOfRangelow() {
		AthleteServices service = getService();
		try {
			@SuppressWarnings("unused")
			List<StravaAthlete> friends = service.listAuthenticatedAthleteFriends(new Paging(-1, -1));
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}
		fail("Illegal paging parameters were accepted");
	}

	@Test
	public void testListAuthenticatedAthleteFriends_pagingIgnoreFirstN() {
		List<StravaAthlete> athletes = getService().listAuthenticatedAthleteFriends(new Paging(1, 2, 1, 0));
		assertNotNull(athletes);
		assertEquals(1, athletes.size());
	}

	@Test
	public void testListAuthenticatedAthleteFriends_pagingIgnoreLastN() {
		List<StravaAthlete> athletes = getService().listAuthenticatedAthleteFriends(new Paging(1, 2, 0, 1));
		assertNotNull(athletes);
		assertEquals(1, athletes.size());
	}

	@Test
	public void testListAthleteFriends_validAthlete() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAthleteFriends(TestUtils.ATHLETE_VALID_ID);
		assertNotNull(friends);
		assertFalse(friends.size() == 0);
		for (StravaAthlete athlete : friends) {
			validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
		}
	}

	// Test cases
	// 2. Invalid athlete
	@Test
	public void testListAthleteFriends_invalidAthlete() {
		AthleteServices service = getService();
		List<StravaAthlete> friends;
		friends = service.listAthleteFriends(TestUtils.ATHLETE_INVALID_ID);

		assertNull("Listed friends despite athlete id being invalid", friends);

	}

	// 4. Paging - size only (including test for max page size)
	@Test
	public void testListAthleteFriends_pageSize() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAthleteFriends(TestUtils.ATHLETE_VALID_ID, new Paging(1, 1));
		assertNotNull(friends);
		assertFalse(friends.size() == 0);
		assertEquals(1, friends.size());
	}

	// 5. Paging - size and page
	@Test
	public void testListAthleteFriends_pageNumberAndSize() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAthleteFriends(TestUtils.ATHLETE_VALID_ID, new Paging(2, 1));
		assertNotNull(friends);
		assertFalse(friends.size() == 0);
		assertEquals(1, friends.size());
		Integer friendId = friends.get(0).getId();
		friends = service.listAthleteFriends(TestUtils.ATHLETE_VALID_ID, new Paging(1, 2));
		assertNotNull(friends);
		assertEquals(2, friends.size());
		assertEquals(friendId, friends.get(1).getId());
	}

	// 6. Paging - out of range high
	@Test
	public void testListAthleteFriends_pagingOutOfRangeHigh() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAthleteFriends(TestUtils.ATHLETE_VALID_ID, new Paging(1000, 200));
		assertNotNull(friends);
		assertEquals(0, friends.size());
	}

	// 7. Paging - out of range low
	@Test
	public void testListAthleteFriends_pagingOutOfRangeLow() {
		AthleteServices service = getService();
		try {
			@SuppressWarnings("unused")
			List<StravaAthlete> friends = service.listAthleteFriends(TestUtils.ATHLETE_VALID_ID, new Paging(-1, -1));
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}
		fail("Listed friends despite paging instructions being illegal");
	}

	@Test
	public void testListAthleteFriends_pagingIgnoreFirstN() {
		List<StravaAthlete> friends = getService().listAthleteFriends(TestUtils.ATHLETE_AUTHENTICATED_ID, new Paging(1, 2, 1, 0));
		assertNotNull(friends);
		assertEquals(1, friends.size());
	}

	@Test
	public void testListAthleteFriends_pagingIgnoreLastN() {
		List<StravaAthlete> friends = getService().listAthleteFriends(TestUtils.ATHLETE_AUTHENTICATED_ID, new Paging(1, 2, 0, 1));
		assertNotNull(friends);
		assertEquals(1, friends.size());
	}

	@Test
	public void testListAthleteFriends_pagingPageSizeTooLarge() {
		List<StravaAthlete> friends = getService().listAthleteFriends(TestUtils.ATHLETE_AUTHENTICATED_ID, new Paging(1, Strava.MAX_PAGE_SIZE + 1));
		assertNotNull(friends);
	}

	// Test cases
	// 1. Valid athlete - at least 1 common friend
	@Test
	public void testListAthletesBothFollowing_validAthlete() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID);
		assertNotNull(friends);
		assertFalse(friends.size() == 0);
		for (StravaAthlete athlete : friends) {
			validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
		}
	}

	// 2. Invalid other athlete
	@Test
	public void testListAthletesBothFollowing_invalidAthlete() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAthletesBothFollowing(TestUtils.ATHLETE_INVALID_ID);
		assertNull("Returned list of athletes being followed by invalid athlete", friends);
	}

	// 4. Paging - size only (including test for max page size)
	@Test
	public void testListAthletesBothFollowing_pageSize() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, new Paging(1, 1));
		assertNotNull(friends);
		assertFalse(friends.size() == 0);
		assertEquals(1, friends.size());
	}

	// 5. Paging - size and page
	@Test
	public void testListAthletesBothFollowing_pageSizeAndNumber() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, new Paging(2, 1));
		assertNotNull(friends);
		assertFalse(friends.size() == 0);
		assertEquals(1, friends.size());
		Integer friendId = friends.get(0).getId();
		friends = service.listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, new Paging(1, 2));
		assertNotNull(friends);
		assertEquals(2, friends.size());
		assertEquals(friendId, friends.get(1).getId());
	}

	// 6. Paging - out of range high
	@Test
	public void testListAthletesBothFollowing_pagingOutOfRangeHigh() {
		AthleteServices service = getService();
		List<StravaAthlete> friends = service.listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, new Paging(1000, 200));
		assertNotNull(friends);
		assertEquals(0, friends.size());
	}

	// 7. Paging - out of range low
	@Test
	public void testListAthletesBothFollowing_pagingOutOfRangeLow() {
		AthleteServices service = getService();
		@SuppressWarnings("unused")
		List<StravaAthlete> friends;
		try {
			friends = service.listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, new Paging(-1, -1));
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}
		fail("Listed friends despite paging instructions being illegal");
	}

	@Test
	public void testListAthletesBothFollowing_pagingIgnoreFirstN() {
		List<StravaAthlete> athletes = getService().listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, new Paging(1, 2, 1, 0));
		assertNotNull(athletes);
		assertEquals(1, athletes.size());
	}

	@Test
	public void testListAthletesBothFollowing_pagingIgnoreLastN() {
		List<StravaAthlete> athletes = getService().listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, new Paging(1, 2, 0, 1));
		assertNotNull(athletes);
		assertEquals(1, athletes.size());
	}

	@Test
	public void testStatistics_authenticatedAthlete() {
		StravaStatistics stats = getService().statistics(TestUtils.ATHLETE_AUTHENTICATED_ID);
		assertNotNull(stats);
	}

	@Test
	public void testStatistics_otherAthlete() {
		StravaStatistics stats = getService().statistics(TestUtils.ATHLETE_VALID_ID);
		assertNotNull(stats);
	}

	@Test
	public void testStatistics_invalidAthlete() {
		StravaStatistics stats = getService().statistics(TestUtils.ATHLETE_INVALID_ID);
		assertNull(stats);
	}
	
	@Test
	// TODO Other test cases
	public void testListAllAthleteFriends() {
		// TODO Not yet implemented
		fail("Not yet implemented");
	}
	
	@Test
	// TODO Other test cases
	public void testListAllAthleteKOMs() {
		// TODO Not yet implemented
		fail("Not yet implemented");
	}
	
	@Test
	// TODO Other test cases
	public void testListAllAthletesBothFollowing() {
		// TODO Not yet implemented
		fail("Not yet implemented");
	}
	
	@Test
	// TODO Other test cases
	public void testListAllAuthenticatedAthleteFriends() {
		// TODO Not yet implemented
		fail("Not yet implemented");
	}

	/**
	 * @return
	 * @throws UnauthorizedException
	 */
	private AthleteServices getService() {
		return AthleteServicesImpl.implementation(TestUtils.getValidToken());
	}

	private AthleteServices getServiceWithoutWriteAccess() {
		return AthleteServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
	}
	
	public static void validateAthlete(StravaAthlete athlete) {
		validateAthlete(athlete, athlete.getId(), athlete.getResourceState());
	}
	
	public static void validateAthlete(StravaAthlete athlete, Integer expectedId, StravaResourceState state) {
		assertNotNull(athlete);
		assertEquals(expectedId, athlete.getId());
		assertEquals(state,athlete.getResourceState());
		
		if (athlete.getResourceState() == StravaResourceState.DETAILED) {
			// Not returned because it's not part of the API for detailed athlete returns
			assertNull(athlete.getApproveFollowers());
			assertNotNull(athlete.getBikes());
			assertNotNull(athlete.getCity());
			assertNotNull(athlete.getClubs());
			assertNotNull(athlete.getCountry());
			assertNotNull(athlete.getCreatedAt());
			assertNotNull(athlete.getDatePreference());
			assertNotNull(athlete.getEmail());
			assertNotNull(athlete.getFirstname());
			// Is NULL because this IS the authenticated athlete and you can't follow yourself 
			assertNull(athlete.getFollower());
			assertNotNull(athlete.getFollowerCount());
			// Is NULL because this is the authenticated athlete and you can't follow yourself
			assertNull(athlete.getFriend());
			assertNotNull(athlete.getFriendCount());
			assertNotNull(athlete.getFtp());
			assertNotNull(athlete.getLastname());
			assertNotNull(athlete.getMeasurementPreference());
			assertFalse(StravaMeasurementMethod.UNKNOWN == athlete.getMeasurementPreference());
			assertNotNull(athlete.getMutualFriendCount());
			assertEquals(new Integer(0),athlete.getMutualFriendCount());
			assertNotNull(athlete.getPremium());
			assertNotNull(athlete.getProfile());
			assertNotNull(athlete.getProfileMedium());
			assertNotNull(athlete.getResourceState());
			assertNotNull(athlete.getSex());
			assertNotNull(athlete.getShoes());
			assertNotNull(athlete.getState());
			assertNotNull(athlete.getUpdatedAt());
			// Not part of detailed data
			assertNull(athlete.getWeight());
			assertNotNull(athlete.getBadgeTypeId());
			return;
		}
		if (athlete.getResourceState() == StravaResourceState.SUMMARY) {
			// Not part of summary data
			assertNull(athlete.getApproveFollowers());
			// Not part of summary data
			assertNull(athlete.getBikes());
			assertNotNull(athlete.getCity());
			// Not part of summary data
			assertNull(athlete.getClubs());
			assertNotNull(athlete.getCountry());
			assertNotNull(athlete.getCreatedAt());
			// Not part of summary data
			assertNull(athlete.getDatePreference());
			// Not part of summary data
			assertNull(athlete.getEmail());
			assertNotNull(athlete.getFirstname());
			if (athlete.getFollower() != null) {
				assertFalse(StravaFollowerState.UNKNOWN == athlete.getFollower());
			}
			// Not part of summary data
			assertNull(athlete.getFollowerCount());
			if (athlete.getFriend() != null) {
				assertFalse(StravaFollowerState.UNKNOWN == athlete.getFriend());
			}
			assertNull(athlete.getFriendCount());
			// Not part of summary data
			assertNull(athlete.getFtp());
			assertNotNull(athlete.getLastname());
			// Not part of summary data
			assertNull(athlete.getMeasurementPreference());
			// Not part of summary data
			assertNull(athlete.getMutualFriendCount());
			assertNotNull(athlete.getPremium());
			assertNotNull(athlete.getProfile());
			assertNotNull(athlete.getProfileMedium());
			// Not part of summary data
			assertNotNull(athlete.getResourceState());
			// Optional
			if (athlete.getSex() != null) {
				assertFalse(athlete.getSex() == StravaGender.UNKNOWN);
			}
			// Not part of summary data
			assertNull(athlete.getShoes());
			assertNotNull(athlete.getState());
			assertNotNull(athlete.getUpdatedAt());
			// Not part of summary data
			assertNull(athlete.getWeight());
			assertNotNull(athlete.getBadgeTypeId());
			return;
		}
		if (athlete.getResourceState() == StravaResourceState.META) {
			assertNull(athlete.getApproveFollowers());
			assertNull(athlete.getBikes());
			assertNull(athlete.getCity());
			assertNull(athlete.getClubs());
			assertNull(athlete.getCountry());
			assertNull(athlete.getCreatedAt());
			assertNull(athlete.getDatePreference());
			assertNull(athlete.getEmail());
			assertNull(athlete.getFirstname());
			assertNull(athlete.getFollower());
			assertNull(athlete.getFollowerCount());
			assertNull(athlete.getFriend());
			assertNull(athlete.getFriendCount());
			assertNull(athlete.getFtp());
			assertNull(athlete.getLastname());
			assertNull(athlete.getMeasurementPreference());
			assertNull(athlete.getMutualFriendCount());
			assertNull(athlete.getPremium());
			assertNull(athlete.getProfile());
			assertNull(athlete.getProfileMedium());
			assertNull(athlete.getSex());
			assertNull(athlete.getShoes());
			assertNull(athlete.getState());
			assertNull(athlete.getUpdatedAt());
			assertNull(athlete.getWeight());
			assertNull(athlete.getBadgeTypeId());
			return;
		}
		fail("Athlete returned with unexpected resource state " + state + " : " + athlete);
	}

}
