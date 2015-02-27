package test.api.service.impl.retrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.StravaActivityZone;
import javastrava.api.v3.model.StravaActivityZoneDistributionBucket;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaBestRunningEffort;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.StravaLap;
import javastrava.api.v3.model.StravaMap;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSplit;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaActivityZoneType;
import javastrava.api.v3.model.reference.StravaPhotoType;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaWorkoutType;
import javastrava.api.v3.service.ActivityServices;
import javastrava.api.v3.service.Strava;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesImpl;
import javastrava.util.Paging;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;
import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * Unit/integration tests for {@link ActivityServicesImpl}
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class ActivityServicesImplTest {

	/**
	 * <p>
	 * Test we get a {@link ActivityServicesImpl service implementation} successfully with a valid token
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Test
	public void testImplementation_validToken() {
		ActivityServices service = getActivityService();
		assertNotNull("Got a NULL service for a valid token", service);
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ActivityServicesImpl service implementation} if the token isn't valid
	 * </p>
	 */
	@Test
	public void testImplementation_invalidToken() {
		try {
			ActivityServices service = ActivityServicesImpl.implementation(TestUtils.INVALID_TOKEN);
			service.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		} catch (UnauthorizedException e) {
			// This is the expected behaviour
			return;
		}
		fail("Got a service for an invalid token!");
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ActivityServicesImpl service implementation} if the token has been revoked by the user
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testImplementation_revokedToken() {
		// Attempt to get an implementation using the now invalidated token
		ActivityServices activityServices = ActivityServicesImpl.implementation(TestUtils.getRevokedToken());

		// Check that it can't be used
		try {
			activityServices.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		} catch (UnauthorizedException e) {
			// Expected behaviour
			return;
		}

		// If we get here, then the service is working despite the token being revoked
		fail("Got a usable service implementation using a revoked token");
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServicesImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the
	 * caching strategy is working)
	 * </p>
	 */
	@Test
	public void testImplementation_implementationIsCached() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		ActivityServices service2 = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServicesImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testImplementation_differentImplementationIsNotCached() {
		String token = TestUtils.getValidToken();
		@SuppressWarnings("unused")
		ActivityServices service = ActivityServicesImpl.implementation(token);
		String token2 = TestUtils.getValidTokenWithoutWriteAccess();
		@SuppressWarnings("unused")
		ActivityServices service2 = ActivityServicesImpl.implementation(token2);
		assertNotEquals("Different tokens returned the same service implementation", token, token2);
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, complete with all {@link StravaSegmentEffort efforts}
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityWithEfforts() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = service.getActivity(TestUtils.ACTIVITY_WITH_EFFORTS, Boolean.TRUE);

		assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_WITH_EFFORTS, activity);
		assertNotNull("StravaActivity " + TestUtils.ACTIVITY_WITH_EFFORTS + " was returned but segmentEfforts is null", activity.getSegmentEfforts());
		assertNotEquals("StravaActivity " + TestUtils.ACTIVITY_WITH_EFFORTS + " was returned but segmentEfforts is empty", 0, activity.getSegmentEfforts()
				.size());
		validateActivity(activity);
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity} that belongs to the authenticated user; it should be a detailed {@link StravaResourceState
	 * representation}
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityBelongsToAuthenticatedUser() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = service.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

		assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, activity);
		assertEquals("Returned activity is not a detailed representation as expected - " + activity.getResourceState(), StravaResourceState.DETAILED,
				activity.getResourceState());
		validateActivity(activity, TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaResourceState.DETAILED);
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity} that DOES NOT belong to the authenticated user; it should be a summary {@link StravaResourceState
	 * representation}
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityBelongsToUnauthenticatedUser() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = service.getActivity(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

		assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, activity);
		assertEquals("Returned activity is not a summary representation as expected - " + activity.getResourceState(), StravaResourceState.SUMMARY,
				activity.getResourceState());
		validateActivity(activity, TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, StravaResourceState.SUMMARY);
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, without the non-important/hidden efforts being returned (i.e. includeAllEfforts = false)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityWithoutEfforts() throws UnauthorizedException {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = service.getActivity(TestUtils.ACTIVITY_WITH_EFFORTS, Boolean.FALSE);

		assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_WITH_EFFORTS, activity);
		assertNotNull("Returned null segment efforts for known activity, when they were expected", activity.getSegmentEfforts());
		validateActivity(activity, TestUtils.ACTIVITY_WITH_EFFORTS, activity.getResourceState());
	}

	/**
	 * <p>
	 * Test retrieval of a non-existent {@link StravaActivity}
	 * </p>
	 * 
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_unknownActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = service.getActivity(TestUtils.ACTIVITY_INVALID);

		assertNull("Got an activity for an invalid activity id " + TestUtils.ACTIVITY_INVALID, activity);
	}

	/**
	 * <p>
	 * Default test to list {@link StravaActivity activities} for the currently authenticated athlete (i.e. the one who corresponds to the current token)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_default() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaActivity> activities = service.listAuthenticatedAthleteActivities();

		assertNotNull("Authenticated athlete's activities returned as null", activities);
		assertNotEquals("No activities returned for the authenticated athlete", 0, activities.size());
		for (StravaActivity activity : activities) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} before a given time (i.e. the before parameter, tested in isolation)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_beforeActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(2015, Calendar.JANUARY, 1);

		List<StravaActivity> activities = service.listAuthenticatedAthleteActivities(calendar, null);
		for (StravaActivity activity : activities) {
			assertTrue(activity.getStartDate().before(calendar.getTime()));
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} after a given time (i.e. the after parameter, tested in isolation)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_afterActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(2015, Calendar.JANUARY, 1);

		List<StravaActivity> activities = service.listAuthenticatedAthleteActivities(null, calendar);
		for (StravaActivity activity : activities) {
			assertTrue(activity.getStartDate().after(calendar.getTime()));
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times (i.e. before and after parameters in combination)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterCombination() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		Calendar before = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		before.set(2015, Calendar.JANUARY, 1);
		Calendar after = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		after.set(2014, Calendar.JANUARY, 1);

		List<StravaActivity> activities = service.listAuthenticatedAthleteActivities(before, after);
		for (StravaActivity activity : activities) {
			assertTrue(activity.getStartDate().before(before.getTime()));
			assertTrue(activity.getStartDate().after(after.getTime()));
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times (i.e. before and after parameters in combination) BUT WITH AN INVALID
	 * COMBINATION OF BEFORE AND AFTER
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterInvalidCombination() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		Calendar before = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		before.set(2014, Calendar.JANUARY, 1);
		Calendar after = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		after.set(2015, Calendar.JANUARY, 1);

		List<StravaActivity> activities = service.listAuthenticatedAthleteActivities(before, after);
		assertNotNull("Returned null collection of activities", activities);
		for (StravaActivity activity : activities) {
			assertTrue(activity.getStartDate().before(before.getTime()));
			assertTrue(activity.getStartDate().after(after.getTime()));
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity, activity.getId(), StravaResourceState.DETAILED);
		}

	}

	/**
	 * <p>
	 * Test paging (page size only)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_pageSize() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaActivity> activities = service.listAuthenticatedAthleteActivities(new Paging(1, 1));

		assertNotNull("Authenticated athlete's activities returned as null when asking for a page of size 1", activities);
		assertEquals("Wrong number of activities returned when asking for a page of size 1", 1, activities.size());
	}

	@Test
	public void testListAuthenticatedAthleteActivities_pageSizeTooLarge() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaActivity> activities = service.listAuthenticatedAthleteActivities(new Paging(2, 201));
		assertNotNull("Returned null list of activities", activities);
		assertEquals(201, activities.size());
		for (StravaActivity activity : activities) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}
	}

	/**
	 * <p>
	 * Test paging (page number and page size).
	 * </p>
	 * 
	 * <p>
	 * To test this we get 2 activities from the service, then ask for the first page only and check that it's the same as the first activity, then ask for the
	 * second page and check that it's the same as the second activity
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_pageNumberAndSize() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaActivity> defaultActivities = service.listAuthenticatedAthleteActivities(new Paging(1, 2));
		for (StravaActivity activity : defaultActivities) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}

		assertEquals("Default page of activities should be of size 2", 2, defaultActivities.size());

		List<StravaActivity> firstPageOfActivities = service.listAuthenticatedAthleteActivities(new Paging(1, 1));
		for (StravaActivity activity : firstPageOfActivities) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}

		assertEquals("First page of activities should be of size 1", 1, firstPageOfActivities.size());
		assertEquals("Different first page of activities to expected", defaultActivities.get(0).getId(), firstPageOfActivities.get(0).getId());

		List<StravaActivity> secondPageOfActivities = service.listAuthenticatedAthleteActivities(new Paging(2, 1));
		for (StravaActivity activity : secondPageOfActivities) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}

		assertEquals("Second page of activities should be of size 1", 1, firstPageOfActivities.size());
		assertEquals("Different second page of activities to expected", defaultActivities.get(1).getId(), secondPageOfActivities.get(0).getId());

	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too high)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_pagingOutOfRangeHigh() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		// Ask for the 200,000th activity by the athlete (this is probably safe!)
		List<StravaActivity> activities = service.listAuthenticatedAthleteActivities(new Paging(1000, 200));

		assertEquals("Unexpected return of activities for paging out of range (high)", 0, activities.size());
	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too low)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_pagingOutOfRangeLow() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		// Ask for the -1th activity by the athlete (this is probably safe!)
		try {
			service.listAuthenticatedAthleteActivities(new Paging(-1, -1));
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}

		fail("Unexpected return of activities for paging out of range (low)");
	}

	/**
	 * Test handling of getting a page of activities and ignoring the first N
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_pagingIgnoreFirstN() {
		List<StravaActivity> activities = getActivityService().listAuthenticatedAthleteActivities(new Paging(1, 2, 1, 0));
		assertNotNull(activities);
		assertEquals(1, activities.size());
		for (StravaActivity activity : activities) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}

		List<StravaActivity> expectedActivities = getActivityService().listAuthenticatedAthleteActivities();
		for (StravaActivity activity : expectedActivities) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}

		assertEquals(expectedActivities.get(1), activities.get(0));
	}

	/**
	 * Test handling of getting a page of activities and ignoring the last N
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_pagingIgnoreLastN() {
		List<StravaActivity> activities = getActivityService().listAuthenticatedAthleteActivities(new Paging(1, 2, 0, 1));
		assertNotNull(activities);
		assertEquals(1, activities.size());
		for (StravaActivity activity : activities) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}

		List<StravaActivity> expectedActivities = getActivityService().listAuthenticatedAthleteActivities();
		for (StravaActivity activity : expectedActivities) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,activity.getAthlete().getId());
			validateActivity(activity);
		}

		assertEquals(expectedActivities.get(0), activities.get(0));
	}

	/**
	 * <p>
	 * List {@link StravaPhoto photos}, with an {@link StravaActivity activity} that has a known non-zero number of photos
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityPhotos_default() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaPhoto> photos = service.listActivityPhotos(TestUtils.ACTIVITY_WITH_PHOTOS);

		assertNotNull("Null list of photos returned for activity", photos);
		assertNotEquals("No photos returned although some were expected", 0, photos.size());
		for (StravaPhoto photo : photos) {
			assertEquals(TestUtils.ACTIVITY_WITH_PHOTOS,photo.getActivityId());
			validatePhoto(photo, photo.getId(), photo.getResourceState());
		}
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaPhoto photos} for a non-existent {@link StravaActivity activity}
	 * </p>
	 * 
	 * <p>
	 * Should return <code>null</code> because the {@link StravaActivity} doesn't exist
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityPhotos_invalidActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaPhoto> photos = service.listActivityPhotos(TestUtils.ACTIVITY_INVALID);

		assertNull("Photos returned for an invalid activity", photos);
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaPhoto photos} for an activity marked as private
	 * </p>
	 * 
	 * <p>
	 * Should return an empty list
	 * </p>
	 */
	@Test
	public void testListActivityPhotos_privateActivity() {
		ActivityServices service = getActivityService();
		List<StravaPhoto> photos = service.listActivityPhotos(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
		assertNotNull(photos);
		assertEquals(0, photos.size());
	}

	/**
	 * <p>
	 * List {@link StravaPhoto photos}, for an {@link StravaActivity activity} that has no photos
	 * </p>
	 * 
	 * <p>
	 * Should return an empty list
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityPhotos_hasNoPhotos() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaPhoto> photos = service.listActivityPhotos(TestUtils.ACTIVITY_WITHOUT_PHOTOS);

		assertNotNull("Photos returned as null for a valid activity without photos", photos);
		assertEquals("Photos were returned for an activity which has no photos", 0, photos.size());
	}

	/**
	 * <p>
	 * Attempt to create a valid manual {@link StravaActivity} for the user associated with the security token
	 * </p>
	 * 
	 * <p>
	 * Should successfully create the activity, and the activity should be retrievable immediately and identical to the one used to create
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testCreateManualActivity_validActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setName("testCreateManualActivity_validActivity");
		activity = service.createManualActivity(TestUtils.createDefaultActivity());

		assertNotNull(activity);

		// Load it from Strava
		StravaActivity stravaActivity = service.getActivity(activity.getId());
		assertNotNull(stravaActivity);
		validateActivity(stravaActivity,stravaActivity.getId(),stravaActivity.getResourceState());

		// And delete it
		service.deleteActivity(activity.getId());
	}

	/**
	 * <p>
	 * Attempt to create a valid manual {@link StravaActivity} for the user associated with the security token, where the user has NOT granted write access via
	 * the OAuth process
	 * </p>
	 * 
	 * <p>
	 * Should fail to create the activity and throw an {@link UnauthorizedException}, which is trapped in the test because it it expected
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testCreateManualActivity_accessTokenDoesNotHaveWriteAccess() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
		StravaActivity activity = null;
		try {
			activity = TestUtils.createDefaultActivity();
			activity.setName("testCreateManualActivity_accessTokenDoesNotHaveWriteAccess");
			activity = service.createManualActivity(activity);
		} catch (UnauthorizedException e) {
			// This is the expected behaviour - creation has failed because there's no write access
			return;
		}

		service.deleteActivity(activity.getId());
		fail("Created a manual activity but should have failed and thrown an UnauthorizedException!");
	}

	/**
	 * <p>
	 * Attempt to create an incomplete manual {@link StravaActivity} for the user where not all required attributes are set
	 * </p>
	 * 
	 * <p>
	 * Should fail to create the activity in each case where a required attribute is missing
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testCreateManualActivity_incompleteActivityDetails() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		// Name is required
		StravaActivity activity = TestUtils.createDefaultActivity();
		StravaActivity stravaResponse = null;
		activity.setName(null);
		activity.setDescription("testCreateManualActivity_incompleteActivityDetails");
		try {
			stravaResponse = service.createManualActivity(activity);
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}
		assertNull("Created an activity with no name in error", stravaResponse);

		// Type is required
		activity = TestUtils.createDefaultActivity();
		activity.setType(null);
		activity.setDescription("testCreateManualActivity_incompleteActivityDetails");
		try {
			stravaResponse = service.createManualActivity(activity);
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}
		assertNull("Created an activity with no type in error", stravaResponse);

		// Type must be one of the specified values
		activity = TestUtils.createDefaultActivity();
		activity.setDescription("testCreateManualActivity_incompleteActivityDetails");
		activity.setType(StravaActivityType.UNKNOWN);
		try {
			stravaResponse = service.createManualActivity(activity);
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}
		assertNull("Created an activity with unknown type in error", stravaResponse);

		// Start date is required
		activity = TestUtils.createDefaultActivity();
		activity.setDescription("testCreateManualActivity_incompleteActivityDetails");
		activity.setStartDateLocal(null);
		try {
			stravaResponse = service.createManualActivity(activity);
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}
		assertNull("Created an activity with no start date in error", stravaResponse);

		// Elapsed time is required
		activity = TestUtils.createDefaultActivity();
		activity.setDescription("testCreateManualActivity_incompleteActivityDetails");
		activity.setElapsedTime(null);
		try {
			stravaResponse = service.createManualActivity(activity);
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}
		assertNull("Created an activity with no elapsed time in error", stravaResponse);
	}

	/**
	 * <p>
	 * Attempt to delete an existing {@link StravaActivity} for the user
	 * </p>
	 * 
	 * <p>
	 * In order to avoid deleting genuine data, this test creates the activity first, checks that it has been successfully written (i.e. that it can be read
	 * back from the API) and then deletes it again
	 * </p>
	 * 
	 * <p>
	 * Should successfully delete the activity; it should no longer be able to be retrieved via the API
	 * </p>
	 */
	@Test
	public void testDeleteActivity_validActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setName("testDeleteActivity_validActivity");
		StravaActivity stravaResponse = service.createManualActivity(activity);
		activity = service.getActivity(stravaResponse.getId());
		assertNotNull(activity);
		service.deleteActivity(activity.getId());

	}

	/**
	 * <p>
	 * Attempt to create an {@link StravaActivity} for the user, using a token which has not been granted write access through the OAuth process
	 * </p>
	 * 
	 * <p>
	 * Should fail to create the activity and throw an {@link UnauthorizedException}
	 * </p>
	 */
	@Test
	public void testDeleteActivity_accessTokenDoesNotHaveWriteAccess() {
		// Create the activity using a service which DOES have write access
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setName("testDeleteActivity_accessTokenDoesNotHaveWriteAccess");
		StravaActivity stravaResponse = service.createManualActivity(activity);
		activity = service.getActivity(stravaResponse.getId());
		assertNotNull(activity);

		// Now get a token without write access and attempt to delete
		service = ActivityServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
		try {
			service.deleteActivity(activity.getId());
			fail("Succeeded in deleting an activity despite not having write access");
		} catch (UnauthorizedException e) {
			// Expected behaviour
		}

		// Delete the activity using a token with write access
		service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		service.deleteActivity(activity.getId());
	}

	/**
	 * <p>
	 * Attempt to delete an {@link StravaActivity} which does not exist
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testDeleteActivity_invalidActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity stravaResponse = service.deleteActivity(1);
		assertNull("deleted an activity that doesn't exist", stravaResponse);
	}

	/**
	 * <p>
	 * List {@link StravaComment comments} for a valid activity
	 * </p>
	 * 
	 * <p>
	 * Expectation is that at least one of the comments contains Markdown; this is tested by checking that at least one comment is different
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_hasComments() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaComment> comments = service.listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.TRUE);

		assertNotNull("Returned null list of comments (with markdown) when some were expected");
		assertNotEquals("Returned empty list of comments when some were expected", 0, comments.size());

		List<StravaComment> commentsWithoutMarkdown = service.listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE);

		// Check that the lists are the same length!!
		assertNotNull("Returned null list of comments (without markdown) when some were expected");
		assertEquals("List of comments for activity " + TestUtils.ACTIVITY_WITH_COMMENTS + " is not same length with/without markdown!", comments.size(),
				commentsWithoutMarkdown.size());
		for (StravaComment comment : comments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}
		for (StravaComment comment : commentsWithoutMarkdown) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}
	}

	/**
	 * <p>
	 * List {@link StravaComment comments} for a valid activity which has no comments
	 * </p>
	 * 
	 * <p>
	 * Should return an empty array of comments
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_hasNoComments() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaComment> comments = service.listActivityComments(TestUtils.ACTIVITY_WITHOUT_COMMENTS, Boolean.TRUE);

		assertNotNull("Returned null list of comments when an empty array was expected", comments);
		assertEquals("Returned a non-empty list of comments when none were expected", 0, comments.size());
		for (StravaComment comment : comments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}
	}

	@Test
	/**
	 * <p>Test paging (page number and page size).</p>
	 * 
	 * <p>To test this we get 2 comments from the service (using the default page with a page size of 2), then ask for the first page only with size 1 and check that it's the same as the first one in the previous list</p> 
	 * 
	 * @throws UnauthorizedException Thrown when security token is invalid
	 */
	public void testListActivityComments_pageNumberAndSize() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaComment> defaultComments = service.listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE, new Paging(1, 2));
		for (StravaComment comment : defaultComments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}

		assertEquals("Default page of comments should be of size 2", 2, defaultComments.size());

		List<StravaComment> firstPageOfComments = service.listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE, new Paging(1, 1));
		for (StravaComment comment : firstPageOfComments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}

		assertEquals("First page of comments should be of size 1", 1, firstPageOfComments.size());
		assertEquals("Different first page of comments to expected", defaultComments.get(0).getId(), firstPageOfComments.get(0).getId());

		List<StravaComment> secondPageOfComments = service.listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE, new Paging(2, 1));
		for (StravaComment comment : secondPageOfComments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}

		assertEquals("Second page of activities should be of size 1", 1, firstPageOfComments.size());
		assertEquals("Different second page of comments to expected", defaultComments.get(1).getId(), secondPageOfComments.get(0).getId());
	}

	/**
	 * <p>
	 * Test page size parameter handling behaves as expected when listing {@link StravaComment comments} for an existing {@link StravaActivity}
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 * @throws NotFoundException
	 */
	@Test
	public void testListActivityComments_pageSize() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaComment> comments = service.listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE, new Paging(1, 1));

		assertNotNull("Asked for one comment in a page, got null", comments);
		assertEquals("Asked for one comment in a page, got " + comments.size(), 1, comments.size());
		for (StravaComment comment : comments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}
	}

	/**
	 * <p>
	 * Test pagination of {@link StravaComment comments} for parameters which are out of range - i.e. too high
	 * </p>
	 * 
	 * <p>
	 * Should return an empty array of {@link StravaComment comments}
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_pagingOutOfRangeHigh() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		// Attempt to get the 200,000th comment, that's probably out of range!
		List<StravaComment> comments = service.listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE, new Paging(1000, 200));

		assertNotNull("Comments should be returned as an empty array, got null", comments);
		assertEquals("Asked for out of range comments, expected an empty array, got " + comments.size() + " comments unexpectedly", 0, comments.size());
	}

	/**
	 * <p>
	 * Test pagination of {@link StravaComment comments} for parameters which are out of range - i.e. too low
	 * </p>
	 * 
	 * <p>
	 * Should throw an {@link IllegalArgumentException} (which will be trapped and ignored by this test)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_pagingOutOfRangeLow() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		try {
			service.listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, Boolean.FALSE, new Paging(-1, -1));
		} catch (IllegalArgumentException e) {
			// Expected behaviour!
			return;
		}

		fail("Paging of comments for out-of-range (low) parameters should have failed, but didn't!");
	}

	@Test
	public void testListActivityComments_pagingIgnoreFirstN() {
		List<StravaComment> expectedComments = getActivityService().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS);
		List<StravaComment> comments = getActivityService().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, new Paging(1, 2, 1, 0));
		assertNotNull(comments);
		assertEquals(1, comments.size());
		assertEquals(expectedComments.get(1), comments.get(0));
		for (StravaComment comment : comments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}
		for (StravaComment comment : expectedComments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}
	}

	@Test
	public void testListActivityComments_pagingIgnoreLastN() {
		List<StravaComment> expectedComments = getActivityService().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS);
		List<StravaComment> comments = getActivityService().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, new Paging(1, 2, 0, 1));
		assertNotNull(comments);
		assertEquals(1, comments.size());
		assertEquals(expectedComments.get(0), comments.get(0));
		for (StravaComment comment : comments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}
		for (StravaComment comment : expectedComments) {
			assertEquals(TestUtils.ACTIVITY_WITH_COMMENTS, comment.getActivityId());
			validateComment(comment, comment.getId(), comment.getResourceState());
		}
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaComment comments} for a non-existent {@link StravaActivity}
	 * </p>
	 * 
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityComments_invalidActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		List<StravaComment> comments;
		comments = service.listActivityComments(TestUtils.ACTIVITY_INVALID, Boolean.FALSE);

		assertNull("Expected null response when retrieving comments for an invalid activity", comments);
	}

	@Test
	public void testListActivityComments_privateActivity() {
		ActivityServices service = getActivityService();
		List<StravaComment> comments = service.listActivityComments(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
		assertNotNull(comments);
		assertEquals(0, comments.size());
	}

	/**
	 * <p>
	 * List {@link StravaAthlete athletes} giving kudos for an {@link StravaActivity} which has >0 kudos
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityKudoers_hasKudoers() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaAthlete> kudoers = service.listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS);

		assertNotNull("Returned null kudos array for activity with kudos", kudoers);
		assertNotEquals("Returned empty kudos array for activity with kudos", 0, kudoers.size());
		for (StravaAthlete athlete : kudoers) {
			AthleteServicesImplTest.validateAthlete(athlete,athlete.getId(),athlete.getResourceState());
		}
	}

	/**
	 * <p>
	 * List {@link StravaAthlete athletes} giving kudos for an {@link StravaActivity} which has NO kudos
	 * </p>
	 * 
	 * <p>
	 * Should return an empty array of {@link StravaAthlete athletes}
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 * @throws NotFoundException
	 */
	@Test
	public void testListActivityKudoers_hasNoKudoers() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaAthlete> kudoers = service.listActivityKudoers(TestUtils.ACTIVITY_WITHOUT_KUDOS);

		assertNotNull("Returned null kudos array for activity without kudos", kudoers);
		assertEquals("Did not return empty kudos array for activity with no kudos", 0, kudoers.size());
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaAthlete athletes} giving kudos for an {@link StravaActivity} which does not exist
	 * </p>
	 * 
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityKudoers_invalidActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaAthlete> kudoers;
		kudoers = service.listActivityKudoers(TestUtils.ACTIVITY_INVALID);

		assertNull("Returned a non-null array of kudoers for an invalid activity", kudoers);
	}

	@Test
	public void testListActivityKudoers_privateActivity() {
		ActivityServices service = getActivityService();
		List<StravaAthlete> kudoers = service.listActivityKudoers(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
		assertNotNull(kudoers);
		assertEquals(0, kudoers.size());
	}

	/**
	 * <p>
	 * Test paging (page number and page size).
	 * </p>
	 * 
	 * <p>
	 * To test this we get 2 kudos from the service (using the default page with a page size of 2), then ask for the first page only with size 1 and check that
	 * it's the same as the first one in the previous list
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityKudoers_pageNumberAndSize() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		List<StravaAthlete> defaultKudoers = service.listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, new Paging(1, 2));
		for (StravaAthlete athlete : defaultKudoers) {
			AthleteServicesImplTest.validateAthlete(athlete,athlete.getId(),athlete.getResourceState());
		}

		assertEquals("Default kudoers should be of length 2", 2, defaultKudoers.size());

		List<StravaAthlete> firstPage = service.listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, new Paging(1, 1));
		for (StravaAthlete athlete : firstPage) {
			AthleteServicesImplTest.validateAthlete(athlete,athlete.getId(),athlete.getResourceState());
		}

		assertEquals("Asking for page of size 1 should return an array of length 1", 1, firstPage.size());
		assertEquals("Page 1 of size 1 should contain the same athlete as the first athlete returned", defaultKudoers.get(0).getId(), firstPage.get(0).getId());

		List<StravaAthlete> secondPage = service.listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, new Paging(2, 1));
		for (StravaAthlete athlete : secondPage) {
			AthleteServicesImplTest.validateAthlete(athlete,athlete.getId(),athlete.getResourceState());
		}

		assertEquals("Asking for page of size 1 should return an array of length 1", 1, secondPage.size());
		assertEquals("Page 2 of size 1 should contain the same athlete as the second athlete returned", defaultKudoers.get(1).getId(), secondPage.get(0)
				.getId());
	}

	/**
	 * <p>
	 * Test page size parameter handling behaves as expected when listing {@link StravaAthlete athletes} giving kudos for an existing {@link StravaActivity}
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 * 
	 */
	@Test
	public void testListActivityKudoers_pageSize() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaAthlete> kudoers = service.listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, new Paging(1, 1));
		for (StravaAthlete athlete : kudoers) {
			AthleteServicesImplTest.validateAthlete(athlete,athlete.getId(),athlete.getResourceState());
		}

		assertNotNull("Asked for one kudoer in a page, got null", kudoers);
		assertEquals("Asked for one kudoer in a page, got " + kudoers.size(), 1, kudoers.size());
	}

	/**
	 * <p>
	 * Attempt to get a result from a pagination result which is way too high
	 * </p>
	 * 
	 * <p>
	 * Should return an empty array
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityKudoers_pagingOutOfRangeHigh() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaAthlete> kudoers = service.listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, new Paging(1000, 200));

		assertNotNull("Kudoers should be returned as an empty array, got null", kudoers);
		assertEquals("Asked for out of range kudos, expected an empty array, got " + kudoers.size() + " kudoers unexpectedly", 0, kudoers.size());
	}

	/**
	 * <p>
	 * Test pagination of {@link StravaAthlete kudoers} for parameters which are out of range - i.e. too low
	 * </p>
	 * 
	 * <p>
	 * Should throw an {@link IllegalArgumentException} (which will be trapped and ignored by this test)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityKudoers_pagingOutOfRangeLow() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		try {
			service.listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, new Paging(-1, -1));
		} catch (IllegalArgumentException e) {
			// Expected behaviour!
			return;
		}

		fail("Paging of kudoers for out-of-range (low) parameters should have failed, but didn't!");
	}

	@Test
	public void testListActivityKudoers_pagingIgnoreFirstN() {
		List<StravaAthlete> athletes = getActivityService().listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, new Paging(1, 2, 1, 0));
		assertNotNull(athletes);
		assertEquals(1, athletes.size());
		for (StravaAthlete athlete : athletes) {
			AthleteServicesImplTest.validateAthlete(athlete,athlete.getId(),athlete.getResourceState());
		}

		List<StravaAthlete> expectedAthletes = getActivityService().listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS);
		assertEquals(expectedAthletes.get(1), athletes.get(0));
		for (StravaAthlete athlete : expectedAthletes) {
			AthleteServicesImplTest.validateAthlete(athlete,athlete.getId(),athlete.getResourceState());
		}

	}

	@Test
	public void testListActivityKudoers_pagingIgnoreLastN() {
		List<StravaAthlete> athletes = getActivityService().listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, new Paging(1, 2, 0, 1));
		assertNotNull(athletes);
		assertEquals(1, athletes.size());
		for (StravaAthlete athlete : athletes) {
			AthleteServicesImplTest.validateAthlete(athlete,athlete.getId(),athlete.getResourceState());
		}

		List<StravaAthlete> expectedAthletes = getActivityService().listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS);
		assertEquals(expectedAthletes.get(0), athletes.get(0));
		for (StravaAthlete athlete : expectedAthletes) {
			AthleteServicesImplTest.validateAthlete(athlete,athlete.getId(),athlete.getResourceState());
		}

	}

	/**
	 * <p>
	 * Attempt to list the {@link StravaLap laps} in an {@link StravaActivity} which has laps
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityLaps_hasLaps() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaLap> laps = service.listActivityLaps(TestUtils.ACTIVITY_WITH_LAPS);

		assertNotNull("Laps not returned for an activity which should have them", laps);
		assertNotEquals("No laps returned for an activity which should have them", 0, laps.size());
		for (StravaLap lap : laps) {
			if (lap.getResourceState() != StravaResourceState.META) {
				assertEquals(TestUtils.ACTIVITY_WITH_LAPS,lap.getActivity().getId());
			}
			validateLap(lap,lap.getId(),lap.getResourceState());
		}
	}

	/**
	 * <p>
	 * Attempt to list the {@link StravaLap laps} in an {@link StravaActivity} which has NO laps
	 * </p>
	 * 
	 * <p>
	 * Should return an empty array of {@link StravaLap}
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityLaps_hasNoLaps() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaLap> laps = service.listActivityLaps(TestUtils.ACTIVITY_WITHOUT_LAPS);

		assertNotNull("Laps not returned for an activity which should have them", laps);
		assertNotEquals("No laps returned for an activity which should have them", 0, laps.size());
		for (StravaLap lap : laps) {
			if (lap.getResourceState() != StravaResourceState.META) {
				assertEquals(TestUtils.ACTIVITY_WITHOUT_LAPS,lap.getActivity().getId());
			}
			validateLap(lap,lap.getId(),lap.getResourceState());
		}
	}

	/**
	 * <p>
	 * Attempt to list the {@link StravaLap laps} in a non-existent {@link StravaActivity}
	 * </p>
	 * 
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityLaps_invalidActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaLap> laps = service.listActivityLaps(TestUtils.ACTIVITY_INVALID);

		assertNull("Laps returned for an invalid activity", laps);
	}

	@Test
	public void testListActivityLaps_privateActivity() {
		ActivityServices service = getActivityService();
		List<StravaLap> laps = service.listActivityLaps(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);

		assertNotNull(laps);
		assertEquals(0, laps.size());
	}

	/**
	 * <p>
	 * List {@link StravaActivityZone activity zones} for an {@link StravaActivity} which has them
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityZones_hasZones() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaActivityZone> zones = service.listActivityZones(TestUtils.ACTIVITY_WITH_ZONES);

		assertNotNull("Returned null activity zones for an activity with zones", zones);
		assertNotEquals("Returned an empty array of activity zones for an activity with zones", 0, zones.size());
		for (StravaActivityZone zone : zones) {
			validateActivityZone(zone,zone.getResourceState());
		}
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaActivityZone zones} for an {@link StravaActivity} which doesn't have any
	 * </p>
	 * 
	 * <p>
	 * Should return an empty array
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityZones_hasNoZones() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaActivityZone> zones = service.listActivityZones(TestUtils.ACTIVITY_WITHOUT_ZONES);

		assertNotNull("Returned null activity zones for an activity without zones (should return an empty array)", zones);
		assertEquals("Returned an non-empty array of activity zones for an activity without zones", 0, zones.size());
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaActivityZone zones} for an {@link StravaActivity} which doesn't exist
	 * </p>
	 * 
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityZones_invalidActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaActivityZone> zones = service.listActivityZones(TestUtils.ACTIVITY_INVALID);

		assertNull("Returned non-null activity zones for an activity which doesn't exist", zones);
	}

	@Test
	public void testListActivityZones_privateActivity() {
		ActivityServices service = getActivityService();
		List<StravaActivityZone> zones = service.listActivityZones(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
		assertNotNull(zones);
		assertEquals(0, zones.size());
	}

	/**
	 * <p>
	 * List latest {@link StravaActivity activities} for {@link StravaAthlete athletes} the currently authorised user is following
	 * </p>
	 * 
	 * <p>
	 * Should return a list of rides in descending order of start date
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListFriendsActivities_hasFriends() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaActivity> activities = service.listFriendsActivities(null);

		assertNotNull("Returned null array for latest friends' activities", activities);

		// Check that the activities are returned in descending order of start date
		Date lastStartDate = null;
		for (StravaActivity activity : activities) {
			if (lastStartDate == null) {
				lastStartDate = activity.getStartDate();
			} else {
				if (activity.getStartDate().after(lastStartDate)) {
					fail("Activities not returned in descending start date order");
				}
			}
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

	/**
	 * <p>
	 * Test paging (page number and page size).
	 * </p>
	 * 
	 * <p>
	 * To test this we get 2 activities from the service, then ask for the first page only and check that it's the same as the first activity, then ask for the
	 * second page and check that it's the same as the second activity
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListFriendsActivities_pageNumberAndSize() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaActivity> defaultActivities = service.listFriendsActivities(new Paging(1, 2));
		for (StravaActivity activity : defaultActivities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}

		assertEquals("Default page of activities should be of size 2", 2, defaultActivities.size());

		List<StravaActivity> firstPageOfActivities = service.listFriendsActivities(new Paging(1, 1));
		for (StravaActivity activity : firstPageOfActivities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}

		assertEquals("First page of activities should be of size 1", 1, firstPageOfActivities.size());
		assertEquals("Different first page of activities to expected", defaultActivities.get(0).getId(), firstPageOfActivities.get(0).getId());

		List<StravaActivity> secondPageOfActivities = service.listFriendsActivities(new Paging(2, 1));
		for (StravaActivity activity : secondPageOfActivities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}

		assertEquals("Second page of activities should be of size 1", 1, secondPageOfActivities.size());
		assertEquals("Different second page of activities to expected", defaultActivities.get(1).getId(), secondPageOfActivities.get(0).getId());

	}

	/**
	 * <p>
	 * Test paging (page size only)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListFriendsActivities_pageSize() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		List<StravaActivity> activities = service.listFriendsActivities(new Paging(1, 1));

		assertNotNull("Authenticated athlete's activities returned as null when asking for a page of size 1", activities);
		assertEquals("Wrong number of activities returned when asking for a page of size 1", 1, activities.size());
		for (StravaActivity activity : activities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too high)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListFriendsActivities_pagingOutOfRangeHigh() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		// Ask for the 2,000,000th activity by the athlete's friends (this is probably safe!)
		List<StravaActivity> activities = service.listFriendsActivities(new Paging(10000, 200));

		assertEquals("Unexpected return of activities for paging out of range (high)", 0, activities.size());
	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too low)
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListFriendsActivities_pagingOutOfRangeLow() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		// Ask for the -1th activity by the athlete (this is probably safe!)
		try {
			service.listFriendsActivities(new Paging(-1, -1));
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		}

		fail("Unexpected return of activities for paging out of range (low)");
	}

	@Test
	public void testListFriendsActivities_pagingIgnoreFirstN() {
		List<StravaActivity> activities = getActivityService().listFriendsActivities(new Paging(1, 2, 1, 0));
		assertNotNull(activities);
		assertEquals(1, activities.size());
		for (StravaActivity activity : activities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

	@Test
	public void testListFriendsActivities_pagingIgnoreLastN() {
		List<StravaActivity> activities = getActivityService().listFriendsActivities(new Paging(1, 2, 0, 1));
		assertNotNull(activities);
		assertEquals(1, activities.size());
		for (StravaActivity activity : activities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
		List<StravaActivity> expectedActivities = getActivityService().listFriendsActivities();
		assertEquals(expectedActivities.get(0), activities.get(0));
		for (StravaActivity activity : expectedActivities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

	/**
	 * <p>
	 * Test cases: allowed to update the following attributes:
	 * </p>
	 * <ol>
	 * <li>name</li>
	 * <li>type</li>
	 * <li>private</li>
	 * <li>commute</li>
	 * <li>trainer</li>
	 * <li>gear_id (also allows special case of 'none' which should remove the gear)</li>
	 * <li>description</li>
	 * </ol>
	 * 
	 */
	@Test
	public void testUpdateActivity_validUpdateName() {
		ActivityServices service = getActivityService();
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setType(StravaActivityType.ALPINE_SKI);
		
		TextProducer text = Fairy.create().textProducer();
		
		StravaActivity response = service.createManualActivity(activity);
		validateActivity(response);
		
		StravaActivityUpdate update = new StravaActivityUpdate();
		String sentence = text.sentence();
		update.setName(sentence);
		
		response = service.updateActivity(response.getId(), update);
		validateActivity(response);
		assertEquals(sentence, response.getName());
		
		service.deleteActivity(response.getId());
	}
	
	@Test
	public void testUpdateActivity_validUpdateType() {
		ActivityServices service = getActivityService();
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setType(StravaActivityType.ALPINE_SKI);
		
		StravaActivity response = service.createManualActivity(activity);
		validateActivity(response);
		
		StravaActivityUpdate update = new StravaActivityUpdate();
		StravaActivityType type = StravaActivityType.RIDE;
		update.setType(type);
		
		response = service.updateActivity(response.getId(), update);
		validateActivity(response);
		assertEquals(type, response.getType());
		
		service.deleteActivity(response.getId());
	}
	
	@Test
	public void testUpdateActivity_validUpdatePrivate() {
		ActivityServices service = getActivityService();
		StravaActivity activity = TestUtils.createDefaultActivity();
		
		StravaActivity response = service.createManualActivity(activity);
		validateActivity(response);
		
		StravaActivityUpdate update = new StravaActivityUpdate();
		Boolean privateFlag = Boolean.TRUE;
		update.setPrivateActivity(privateFlag);
		
		response = service.updateActivity(response.getId(), update);
		validateActivity(response);
		assertEquals(privateFlag, response.getPrivateActivity());
		
		service.deleteActivity(response.getId());
	}
	
	@Test
	public void testUpdateActivity_validUpdateCommute() {
		ActivityServices service = getActivityService();
		StravaActivity activity = TestUtils.createDefaultActivity();
		
		StravaActivity response = service.createManualActivity(activity);
		validateActivity(response);
		
		StravaActivityUpdate update = new StravaActivityUpdate();
		Boolean commute = Boolean.TRUE;
		update.setCommute(commute);
		
		response = service.updateActivity(response.getId(), update);
		validateActivity(response);
		assertEquals(commute, response.getCommute());
		
		service.deleteActivity(response.getId());
	}
	
	@Test
	public void testUpdateActivity_validUpdateTrainer() {
		ActivityServices service = getActivityService();
		StravaActivity activity = TestUtils.createDefaultActivity();
		
		StravaActivity response = service.createManualActivity(activity);
		validateActivity(response);
		
		StravaActivityUpdate update = new StravaActivityUpdate();
		Boolean trainer = Boolean.TRUE;
		update.setTrainer(trainer);
		
		response = service.updateActivity(response.getId(), update);
		validateActivity(response);
		assertEquals(trainer, response.getTrainer());
		
		service.deleteActivity(response.getId());
	}
	
	@Test
	public void testUpdateActivity_validUpdateGearId() {
		ActivityServices service = getActivityService();
		StravaActivity activity = TestUtils.createDefaultActivity();
		
		StravaActivity response = service.createManualActivity(activity);
		validateActivity(response);
		
		StravaActivityUpdate update = new StravaActivityUpdate();
		String gearId = TestUtils.GEAR_VALID_ID;
		update.setGearId(gearId);
		
		response = service.updateActivity(response.getId(), update);
		validateActivity(response);
		assertEquals(gearId, response.getGearId());
		
		service.deleteActivity(response.getId());
	}
	
	@Test
	public void testUpdateActivity_validUpdateGearIDNone() {
		ActivityServices service = getActivityService();
		StravaActivity activity = TestUtils.createDefaultActivity();
		
		StravaActivity response = service.createManualActivity(activity);
		validateActivity(response);
		
		StravaActivityUpdate update = new StravaActivityUpdate();
		String gearId = "none";
		update.setGearId(gearId);
		
		response = service.updateActivity(response.getId(), update);
		validateActivity(response);
		assertNull(response.getGearId());
		
		service.deleteActivity(response.getId());
	}
	
	@Test
	public void testUpdateActivity_validUpdateDescription() {
		ActivityServices service = getActivityService();
		StravaActivity activity = TestUtils.createDefaultActivity();
		
		StravaActivity response = service.createManualActivity(activity);
		validateActivity(response);
		
		TextProducer text = Fairy.create().textProducer();
		StravaActivityUpdate update = new StravaActivityUpdate();
		String description = text.sentence();
		update.setDescription(description);
		
		response = service.updateActivity(response.getId(), update);
		validateActivity(response);
		assertEquals(description, response.getDescription());
		
		service.deleteActivity(response.getId());
	}
	
	@Test
	public void testUpdateActivity_validUpdateAllAtOnce() {
		ActivityServices service = getActivityService();
		StravaActivity activity = TestUtils.createDefaultActivity();
		
		StravaActivity response = service.createManualActivity(activity);
		validateActivity(response);
		
		TextProducer text = Fairy.create().textProducer();
		String description = text.sentence();
		String name = text.sentence();
		StravaActivityType type = StravaActivityType.RUN;
		Boolean privateActivity = Boolean.TRUE;
		Boolean commute = Boolean.TRUE;
		Boolean trainer = Boolean.TRUE;
		String gearId = TestUtils.GEAR_VALID_ID;
		
		StravaActivityUpdate update = new StravaActivityUpdate();
		update.setDescription(description);
		update.setCommute(commute);
		update.setGearId(gearId);
		update.setName(name);
		update.setPrivateActivity(privateActivity);
		update.setTrainer(trainer);
		update.setType(type);
		
		response = service.updateActivity(response.getId(), update);
		validateActivity(response);
		assertEquals(description, response.getDescription());
		assertEquals(commute, response.getCommute());
		assertEquals(gearId, response.getGearId());
		assertEquals(name, response.getName());
		assertEquals(privateActivity, response.getPrivateActivity());
		assertEquals(trainer, response.getTrainer());
		assertEquals(type, response.getType());
		
		service.deleteActivity(response.getId());
	}
	
	@Test
	public void testUpdateActivity_tooManyActivityAttributes() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setName("testUpdateActivity_tooManyActivityAttributes");
		StravaActivity stravaResponse = service.createManualActivity(activity);

		Float cadence = new Float(67.2);
		activity.setAverageCadence(cadence);
		activity.setId(stravaResponse.getId());

		stravaResponse = service.updateActivity(stravaResponse.getId(), new StravaActivityUpdate(activity));
		
		assertNull(stravaResponse.getAverageCadence());
		validateActivity(stravaResponse);

		service.deleteActivity(stravaResponse.getId());
	}

	@Test
	public void testUpdateActivity_invalidActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setId(TestUtils.ACTIVITY_INVALID);

		StravaActivity response = service.updateActivity(activity.getId(), new StravaActivityUpdate(activity));
		assertNull("Updated an activity which doesn't exist?", response);
	}

	@Test
	public void testUpdateActivity_unauthenticatedAthletesActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = service.getActivity(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

		try {
			service.updateActivity(activity.getId(), new StravaActivityUpdate(activity));
		} catch (UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Updated an activity which belongs to someone else??");
	}

	/**
	 * <p>
	 * Test attempting to update an activity using a token that doesn't have write access
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testUpdateActivity_accessTokenDoesNotHaveWriteAccess() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
		Fairy fairy = Fairy.create();
		TextProducer text = fairy.textProducer();
		StravaActivity activity = service.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		activity.setDescription(text.paragraph(1));

		try {
			service.updateActivity(activity.getId(), new StravaActivityUpdate(activity));
		} catch (UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Successfully updated an activity despite not having write access");
	}

	@Test
	public void testGetActivity_privateBelongsToOtherUser() {
		ActivityServices service = getActivityService();
		StravaActivity activity = service.getActivity(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);

		// Should get an activity which only has an id
		assertNotNull(activity);
		StravaActivity comparisonActivity = new StravaActivity();
		comparisonActivity.setId(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
		comparisonActivity.setResourceState(StravaResourceState.META);
		assertEquals(comparisonActivity, activity);
		validateActivity(activity);
	}

	@Test
	public void testListRelatedActivities_validActivity() {
		ActivityServices service = getActivityService();
		List<StravaActivity> activities = service.listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(activities);
		for (StravaActivity activity : activities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}

	}

	@Test
	public void testListRelatedActivities_invalidActivity() {
		ActivityServices service = getActivityService();
		List<StravaActivity> activities = service.listRelatedActivities(TestUtils.ACTIVITY_INVALID);
		assertNull(activities);
	}

	@Test
	public void testListRelatedActivities_privateActivity() {
		ActivityServices service = getActivityService();
		List<StravaActivity> activities = service.listRelatedActivities(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
		assertNotNull(activities);
		assertEquals(0, activities.size());
	}

	private ActivityServices getActivityService() {
		return ActivityServicesImpl.implementation(TestUtils.getValidToken());
	}

	@Test
	public void testListRelatedActivities_pageNumberAndSize() {
		List<StravaActivity> activities = getActivityService().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, new Paging(1, 2));
		for (StravaActivity activity : activities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
		List<StravaActivity> page1 = getActivityService().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, new Paging(1, 1));
		assertNotNull(page1);
		for (StravaActivity activity : page1) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}

		List<StravaActivity> page2 = getActivityService().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, new Paging(2, 1));
		assertNotNull(page2);
		for (StravaActivity activity : page2) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}

		assertEquals(1, page1.size());
		assertEquals(1, page2.size());
		assertEquals(activities.get(0), page1.get(0));
		assertEquals(activities.get(1), page2.get(0));
	}

	@Test
	public void testListRelatedActivities_pageSize() {
		List<StravaActivity> activities = getActivityService().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, new Paging(1, 1));
		assertNotNull(activities);
		assertEquals(1,activities.size());
		for (StravaActivity activity : activities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

	@Test
	public void testListRelatedActivities_pagingIgnoreFirstN() {
		List<StravaActivity> activities = getActivityService().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, new Paging(1, 2, 1, 0));
		List<StravaActivity> expectedActivities = getActivityService().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(activities);
		for (StravaActivity activity : activities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
		
		assertEquals(1,activities.size());
		assertEquals(expectedActivities.get(1),activities.get(0));
		for (StravaActivity activity : expectedActivities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

	@Test
	public void testListRelatedActivities_pagingIgnoreLastN() {
		List<StravaActivity> activities = getActivityService().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, new Paging(1, 2, 0, 1));
		List<StravaActivity> expectedActivities = getActivityService().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(activities);
		for (StravaActivity activity : activities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
		assertEquals(1, activities.size());
		assertEquals(expectedActivities.get(0), activities.get(0));
		for (StravaActivity activity : expectedActivities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

	@Test
	public void testListRelatedActivities_pagingOutOfRangeHigh() {
		List<StravaActivity> activities = getActivityService().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER,
				new Paging(1000, Strava.MAX_PAGE_SIZE));
		assertNotNull(activities);
		assertEquals(0, activities.size());
	}

	@Test
	public void testListRelatedActivities_pagingOutOfRangeLow() {
		try {
			getActivityService().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, new Paging(-1, -1));
		} catch (IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Shouldn't be returning page -1!");
	}

	@Test
	// TODO Other test cases (before, after, both, invalid)
	public void testListAllAuthenticatedAthleteActivities() {
		List<StravaActivity> activities = getActivityService().listAllAuthenticatedAthleteActivities();
		assertNotNull(activities);
		for (StravaActivity activity : activities) {
			validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}
	
	@Test
	// TODO Other test cases
	public void testCreateComment() throws NotFoundException {
		ActivityServices service = getActivityService();
		StravaComment comment = service.createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
		validateComment(comment);
		service.deleteComment(comment.getActivityId(), comment.getId());
		
	}
	
	@Test
	// TODO Other test cases
	public void testGiveKudos() {
		getActivityService().giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
		List<StravaAthlete> kudoers = getActivityService().listActivityKudoers(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
		
		// Check that kudos is now given
		boolean found = false;
		for (StravaAthlete athlete : kudoers) {
			AthleteServicesImplTest.validateAthlete(athlete);
			if (athlete.getId() == TestUtils.ATHLETE_AUTHENTICATED_ID) {
				found = true;
			}
		}
		assertTrue(found);
	}
	
	@Test
	// TODO Other test cases
	public void testListAllRelatedActivities() {
		// TODO Not yet implemented
		fail("Not yet implemented");
	}
	
	@Test
	// TODO Other test cases including an attempt to get > 200??
	public void testListAllFriendsActivities() {
		// TODO Not yet implemented
		fail("Not yet implemented");
	}
	
	public static void validateActivity(StravaActivity activity) {
		validateActivity(activity, activity.getId(), activity.getResourceState());
	}
	
	public static void validateActivity(StravaActivity activity, Integer id, StravaResourceState state) {
		assertNotNull(activity);
		assertEquals(id,activity.getId());
		assertEquals(state,activity.getResourceState());
		
		if (state == StravaResourceState.DETAILED) {
			assertNotNull(activity.getAchievementCount());
			assertNotNull(activity.getAthlete());
			assertTrue(activity.getAthlete().getResourceState() == StravaResourceState.META || activity.getAthlete().getResourceState() == StravaResourceState.SUMMARY);
			AthleteServicesImplTest.validateAthlete(activity.getAthlete(), activity.getAthlete().getId(), activity.getAthlete().getResourceState());
			assertNotNull(activity.getName());
			assertNotNull(activity.getDistance());
			assertTrue(activity.getDistance() > 0);
			assertNotNull(activity.getAthleteCount());
			if (activity.getType() == StravaActivityType.RIDE) {
				if (activity.getAverageCadence() != null) {
					assertTrue(activity.getAverageCadence() >= 0);
				}
				if (activity.getAverageWatts() != null) {
					assertTrue(activity.getAverageWatts() >= 0);
				}
				assertNotNull(activity.getDeviceWatts());
				if (activity.getDeviceWatts()) {
					assertNotNull(activity.getWeightedAverageWatts());
				}
			} else {
				assertNull(activity.getAverageCadence());
				assertNull(activity.getAverageWatts());
				assertNull(activity.getDeviceWatts());
				assertNull(activity.getWeightedAverageWatts());
			}
			if (activity.getAverageHeartrate() != null) {
				assertTrue(activity.getAverageHeartrate() >= 0);
				assertNotNull(activity.getMaxHeartrate());
				assertTrue(activity.getMaxHeartrate() >= activity.getAverageHeartrate());
			}
			if (activity.getMaxHeartrate() != null) {
				assertNotNull(activity.getAverageHeartrate());
			}
			
			assertNotNull(activity.getAverageSpeed());
			// Optional assertNotNull(activity.getAverageTemp());
			if (activity.getType() == StravaActivityType.RUN) {
				assertNotNull(activity.getBestEfforts());
				for (StravaBestRunningEffort effort : activity.getBestEfforts()) {
					validateBestEffort(effort,effort.getId(),effort.getResourceState());
				}
				if (!activity.getManual()) {
					assertNotNull(activity.getSplitsMetric());
					for (StravaSplit split : activity.getSplitsMetric()) {
						validateSplit(split);
					}
					assertNotNull(activity.getSplitsStandard());
					for (StravaSplit split : activity.getSplitsStandard()) {
						validateSplit(split);
					}
				}
			}
			assertNotNull(activity.getCalories());
			assertNotNull(activity.getCommentCount());
			assertNotNull(activity.getCommute());
			// OPTIONAL assertNotNull(activity.getDescription());
			assertNotNull(activity.getElapsedTime());
			// Optional (because sometimes GPS doesn't work properly) assertNull(activity.getEndLatlng());
			// Optional assertNotNull(activity.getExternalId());
			assertNotNull(activity.getFlagged());
			if (activity.getGear() != null) {
				GearServicesImplTest.validateGear(activity.getGear(),activity.getGearId(),activity.getGear().getResourceState());
			}
			assertNotNull(activity.getHasKudoed());
			if (activity.getType() == StravaActivityType.RIDE && !activity.getManual() && !activity.getTrainer() && activity.getCalories() != null) {
				assertNotNull(activity.getKilojoules());
			}
			assertNotNull(activity.getKudosCount());
			// Optional assertNotNull(activity.getLocationCity());
			// Optional assertNotNull(activity.getLocationCountry());
			// Optional assertNotNull(activity.getLocationState());
			assertNotNull(activity.getManual());
			assertNotNull(activity.getMap());
			if (!activity.getManual() && !activity.getTrainer()) {
				validateMap(activity.getMap(),activity.getMap().getId(),activity.getMap().getResourceState(), activity);
			}
			assertNotNull(activity.getMaxSpeed());
			assertTrue(activity.getMaxSpeed() >= 0);
			assertNotNull(activity.getMovingTime());
			assertNotNull(activity.getName());
			assertNotNull(activity.getPhotoCount());
			assertNotNull(activity.getPrivateActivity());
			assertNotNull(activity.getSegmentEfforts());
			for (StravaSegmentEffort effort : activity.getSegmentEfforts()) {
				SegmentEffortServicesImplTest.validateSegmentEffort(effort, effort.getId(), effort.getResourceState());
			}
			assertNotNull(activity.getStartDate());
			assertNotNull(activity.getStartDateLocal());
			// Optional (because sometimes GPS doesn't work properly) assertNull(activity.getStartLatlng());
			assertNotNull(activity.getTimezone());
			assertNotNull(activity.getTotalElevationGain());
			assertNotNull(activity.getTrainer());
			if (activity.getAthlete().getId().intValue() != TestUtils.ATHLETE_AUTHENTICATED_ID) {
				assertNull(activity.getTruncated());
			}
			assertNotNull(activity.getType());
			assertFalse(activity.getType() == StravaActivityType.UNKNOWN);
			if (activity.getType() != StravaActivityType.RUN) {
				assertNull(activity.getWorkoutType());
			}
			if (activity.getWorkoutType() != null) {
				assertFalse(activity.getWorkoutType() == StravaWorkoutType.UNKNOWN);
			}
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(activity.getAchievementCount());
			assertNotNull(activity.getAthlete());
			assertTrue(activity.getAthlete().getResourceState() == StravaResourceState.META || activity.getAthlete().getResourceState() == StravaResourceState.SUMMARY);
			AthleteServicesImplTest.validateAthlete(activity.getAthlete(), activity.getAthlete().getId(), activity.getAthlete().getResourceState());
			assertNotNull(activity.getName());
			assertNotNull(activity.getDistance());
			assertTrue(activity.getDistance() > 0);
			assertNotNull(activity.getAthleteCount());
			if (activity.getType() == StravaActivityType.RIDE) {
				if (activity.getAverageCadence() != null) {
					assertTrue(activity.getAverageCadence() >= 0);
				}
				if (activity.getAverageWatts() != null) {
					assertTrue(activity.getAverageWatts() >= 0);
				}
				assertNotNull(activity.getDeviceWatts());
				if (activity.getDeviceWatts()) {
					assertNotNull(activity.getWeightedAverageWatts());
				}
			} else {
				assertNull(activity.getAverageCadence());
				assertNull(activity.getAverageWatts());
				assertNull(activity.getDeviceWatts());
				assertNull(activity.getWeightedAverageWatts());
			}
			if (activity.getAverageHeartrate() != null) {
				assertTrue(activity.getAverageHeartrate() >= 0);
				assertNotNull(activity.getMaxHeartrate());
				assertTrue(activity.getMaxHeartrate() >= activity.getAverageHeartrate());
			}
			if (activity.getMaxHeartrate() != null) {
				assertNotNull(activity.getAverageHeartrate());
			}
			assertNotNull(activity.getAverageSpeed());
			// Optional assertNotNull(activity.getAverageTemp());
			assertNull(activity.getBestEfforts());
			assertNull(activity.getCalories());
			assertNotNull(activity.getCommentCount());
			assertNotNull(activity.getCommute());
			assertNull(activity.getDescription());
			assertNotNull(activity.getElapsedTime());
			// Optional (because sometimes GPS doesn't work properly) assertNull(activity.getEndLatlng());
			// Optional assertNotNull(activity.getExternalId());
			assertNotNull(activity.getFlagged());
			assertNull(activity.getGear());
			// Optional assertNotNull(activity.getGearId());
			assertNotNull(activity.getHasKudoed());
			if (activity.getType() == StravaActivityType.RIDE && !activity.getManual() && !activity.getTrainer() && activity.getCalories() != null) {
				assertNotNull(activity.getKilojoules());
			}
			assertNotNull(activity.getKudosCount());
			// Optional assertNotNull(activity.getLocationCity());
			// Optional assertNotNull(activity.getLocationCountry());
			// Optional assertNotNull(activity.getLocationState());
			assertNotNull(activity.getManual());
			assertNotNull(activity.getMap());
			if (!activity.getManual() && !activity.getTrainer()) {
				validateMap(activity.getMap(),activity.getMap().getId(),activity.getMap().getResourceState(), activity);
			}
			assertNotNull(activity.getMaxSpeed());
			assertTrue(activity.getMaxSpeed() >= 0);
			assertNotNull(activity.getMovingTime());
			assertNotNull(activity.getName());
			assertNotNull(activity.getPhotoCount());
			assertNotNull(activity.getPrivateActivity());
			assertNull(activity.getSegmentEfforts());
			assertNull(activity.getSplitsMetric());
			assertNull(activity.getSplitsStandard());
			assertNotNull(activity.getStartDate());
			assertNotNull(activity.getStartDateLocal());
			// Optional (because sometimes GPS doesn't work properly) assertNull(activity.getStartLatlng());
			assertNotNull(activity.getTimezone());
			assertNotNull(activity.getTotalElevationGain());
			assertNotNull(activity.getTrainer());
			if (activity.getAthlete().getId().intValue() != TestUtils.ATHLETE_AUTHENTICATED_ID) {
				assertNull(activity.getTruncated());
			}
			assertNotNull(activity.getType());
			assertFalse(activity.getType() == StravaActivityType.UNKNOWN);
			if (activity.getType() != StravaActivityType.RUN) {
				assertNull(activity.getWorkoutType());
			}
			if (activity.getWorkoutType() != null) {
				assertFalse(activity.getWorkoutType() == StravaWorkoutType.UNKNOWN);
			}
			return;
		}
		if (state == StravaResourceState.META) {
			assertNull(activity.getAchievementCount());
			assertNull(activity.getAthlete());
			assertNull(activity.getName());
			assertNull(activity.getDistance());
			assertNull(activity.getAthleteCount());
			assertNull(activity.getAverageCadence());
			assertNull(activity.getAverageHeartrate());
			assertNull(activity.getAverageSpeed());
			assertNull(activity.getAverageTemp());
			assertNull(activity.getAverageWatts());
			assertNull(activity.getBestEfforts());
			assertNull(activity.getCalories());
			assertNull(activity.getCommentCount());
			assertNull(activity.getCommute());
			assertNull(activity.getDescription());
			assertNull(activity.getDeviceWatts());
			assertNull(activity.getElapsedTime());
			assertNull(activity.getEndLatlng());
			assertNull(activity.getExternalId());
			assertNull(activity.getFlagged());
			assertNull(activity.getGear());
			assertNull(activity.getGearId());
			assertNull(activity.getHasKudoed());
			assertNull(activity.getKilojoules());
			assertNull(activity.getKudosCount());
			assertNull(activity.getLocationCity());
			assertNull(activity.getLocationCountry());
			assertNull(activity.getLocationState());
			assertNull(activity.getManual());
			assertNull(activity.getMap());
			assertNull(activity.getMaxHeartrate());
			assertNull(activity.getMaxSpeed());
			assertNull(activity.getMovingTime());
			assertNull(activity.getName());
			assertNull(activity.getPhotoCount());
			assertNull(activity.getPrivateActivity());
			assertNull(activity.getSegmentEfforts());
			assertNull(activity.getSplitsMetric());
			assertNull(activity.getSplitsStandard());
			assertNull(activity.getStartDate());
			assertNull(activity.getStartDateLocal());
			assertNull(activity.getStartLatlng());
			assertNull(activity.getTimezone());
			assertNull(activity.getTotalElevationGain());
			assertNull(activity.getTrainer());
			assertNull(activity.getTruncated());
			assertNull(activity.getType());
			assertNull(activity.getWeightedAverageWatts());
			assertNull(activity.getWorkoutType());
			return;
		}
		fail("Unexpected activity state " + state + " for activity " + activity);
	}

	/**
	 * @param split
	 */
	private static void validateSplit(StravaSplit split) {
		assertNotNull(split);
		assertNotNull(split.getDistance());
		assertNotNull(split.getElapsedTime());
		assertNotNull(split.getElevationDifference());
		assertNotNull(split.getMovingTime());
		assertNotNull(split.getSplit());
		
	}

	/**
	 * @param effort
	 * @param id
	 * @param resourceState
	 */
	public static void validateBestEffort(StravaBestRunningEffort effort, Integer id, StravaResourceState state) {
		assertNotNull(effort);
		assertEquals(id, effort.getId());
		assertEquals(state, effort.getResourceState());
		
		if (state == StravaResourceState.DETAILED) {
			assertNotNull(effort.getActivity());
			// NB Don't validate the activity - that way lies 1 Infinite Loop
			assertNotNull(effort.getAthlete());
			AthleteServicesImplTest.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(), effort.getAthlete().getResourceState());
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			assertNotNull(effort.getKomRank());
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			assertNotNull(effort.getPrRank());
			assertNotNull(effort.getSegment());
			SegmentServicesImplTest.validateSegment(effort.getSegment(), effort.getSegment().getId(), effort.getSegment().getResourceState());
			assertNotNull(effort.getStartDate());
			assertNotNull(effort.getStartDateLocal());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(effort.getActivity());
			// NB Don't validate the activity - that way lies 1 Infinite Loop
			assertNotNull(effort.getAthlete());
			AthleteServicesImplTest.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(), effort.getAthlete().getResourceState());
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			assertNotNull(effort.getKomRank());
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			assertNotNull(effort.getPrRank());
			assertNotNull(effort.getSegment());
			SegmentServicesImplTest.validateSegment(effort.getSegment(), effort.getSegment().getId(), effort.getSegment().getResourceState());
			assertNotNull(effort.getStartDate());
			assertNotNull(effort.getStartDateLocal());
			return;
		}
		if (state == StravaResourceState.META) {
			assertNull(effort.getActivity());
			// NB Don't validate the activity - that way lies 1 Infinite Loop
			assertNull(effort.getAthlete());
			assertNull(effort.getDistance());
			assertNull(effort.getElapsedTime());
			assertNull(effort.getKomRank());
			assertNull(effort.getMovingTime());
			assertNull(effort.getName());
			assertNull(effort.getPrRank());
			assertNull(effort.getSegment());
			assertNull(effort.getStartDate());
			assertNull(effort.getStartDateLocal());
			return;
		}
		fail("Unexpected state " + state + " for best effort " + effort);

	}

	/**
	 * @param map
	 * @param id
	 * @param state
	 */
	public static void validateMap(StravaMap map, String id, StravaResourceState state, StravaActivity activity) {
		assertNotNull(map);
		assertEquals(id,map.getId());
		assertEquals(state,map.getResourceState());
		
		if (activity != null && (activity.getManual() || activity.getTrainer())) {
			return;
		}
		
		if (state == StravaResourceState.DETAILED) {
			assertNotNull(map.getPolyline());
			assertNotNull(map.getSummaryPolyline());
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNull(map.getPolyline());
			// Optional assertNotNull(map.getSummaryPolyline());
		}
		if (state == StravaResourceState.META) {
			assertNotNull(map.getPolyline());
			assertNotNull(map.getSummaryPolyline());
		}
		if (state == StravaResourceState.UNKNOWN || state == StravaResourceState.UPDATING) {
			fail("Unexpected state " + state + " for map " + map);
		}
		
	}
	
	public static void validatePhoto(StravaPhoto photo, Integer id, StravaResourceState state) {
		assertNotNull(photo);
		assertEquals(id,photo.getId());
		assertEquals(state,photo.getResourceState());
		
		if (state == StravaResourceState.DETAILED) {
			assertNotNull(photo.getCaption());
			assertNotNull(photo.getCreatedAt());
			// Optional assertNotNull(photo.getLocation());
			assertNotNull(photo.getRef());
			assertNotNull(photo.getType());
			assertFalse(photo.getType() == StravaPhotoType.UNKNOWN);
			assertNotNull(photo.getUid());
			assertNotNull(photo.getUploadedAt());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(photo.getCaption());
			assertNotNull(photo.getCreatedAt());
			// Optional assertNotNull(photo.getLocation());
			assertNotNull(photo.getRef());
			assertNotNull(photo.getType());
			assertFalse(photo.getType() == StravaPhotoType.UNKNOWN);
			assertNotNull(photo.getUid());
			assertNotNull(photo.getUploadedAt());
			return;
		}
		if (state == StravaResourceState.META) {
			assertNull(photo.getCaption());
			assertNull(photo.getCreatedAt());
			assertNull(photo.getLocation());
			assertNull(photo.getRef());
			assertNull(photo.getType());
			assertNull(photo.getUid());
			assertNull(photo.getUploadedAt());
			return;
		}
		fail("Unexpected resource state " + state + " for photo " + photo);
	}
	
	public static void validateComment(StravaComment comment) {
		validateComment(comment, comment.getId(), comment.getResourceState());
	}
	
	public static void validateComment(StravaComment comment, Integer id, StravaResourceState state) {
		assertNotNull(comment);
		assertEquals(id, comment.getId());
		assertEquals(state, comment.getResourceState());
		
		if (state == StravaResourceState.DETAILED) {
			assertNotNull(comment.getActivityId());
			assertNotNull(comment.getAthlete());
			AthleteServicesImplTest.validateAthlete(comment.getAthlete(), comment.getAthlete().getId(), comment.getAthlete().getResourceState());
			assertNotNull(comment.getCreatedAt());
			assertNotNull(comment.getText());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(comment.getActivityId());
			assertNotNull(comment.getAthlete());
			AthleteServicesImplTest.validateAthlete(comment.getAthlete(), comment.getAthlete().getId(), comment.getAthlete().getResourceState());
			assertNotNull(comment.getCreatedAt());
			assertNotNull(comment.getText());
			return;
		}
		if (state == StravaResourceState.META) {
			assertNull(comment.getActivityId());
			assertNull(comment.getAthlete());
			assertNull(comment.getCreatedAt());
			assertNull(comment.getText());
			return;
		}
		fail("Unexpected resource state " + state + " for comment " + comment);
	}
	
	public static void validateLap(StravaLap lap, Integer id, StravaResourceState state) {
		assertNotNull(lap);
		assertEquals(id,lap.getId());
		assertEquals(state,lap.getResourceState());
		
		if (state == StravaResourceState.DETAILED) {
			assertNotNull(lap.getActivity());
			validateActivity(lap.getActivity(), lap.getActivity().getId(), lap.getActivity().getResourceState());
			assertNotNull(lap.getAthlete());
			AthleteServicesImplTest.validateAthlete(lap.getAthlete(),lap.getAthlete().getId(),lap.getAthlete().getResourceState());
			if (lap.getAverageCadence() != null) {
				assertTrue(lap.getAverageCadence() >= 0);
			}
			if (lap.getAverageHeartrate() != null) { 
				assertTrue(lap.getAverageHeartrate() >= 0);
				assertNotNull(lap.getMaxHeartrate());
				assertTrue(lap.getMaxHeartrate() >= lap.getAverageHeartrate());
			}
			if (lap.getMaxHeartrate() != null) {
				assertNotNull(lap.getAverageHeartrate());
			}
			assertNotNull(lap.getAverageSpeed());
			assertTrue(lap.getAverageSpeed() >= 0);
			if (lap.getAverageWatts() != null) {
				assertTrue(lap.getAverageWatts() >= 0);
				assertNotNull(lap.getDeviceWatts());
			}
			assertNotNull(lap.getDistance());
			assertTrue(lap.getDistance() >= 0);
			assertNotNull(lap.getElapsedTime());
			assertTrue(lap.getElapsedTime() >= 0);
			assertNotNull(lap.getEndIndex());
			assertNotNull(lap.getLapIndex());
			assertNotNull(lap.getMaxSpeed());
			assertTrue(lap.getMaxSpeed() >= lap.getAverageSpeed());
			assertNotNull(lap.getMovingTime());
			assertTrue(lap.getMovingTime() >= 0);
			assertNotNull(lap.getName());
			assertNotNull(lap.getStartDate());
			assertNotNull(lap.getStartDateLocal());
			assertNotNull(lap.getStartIndex());
			assertTrue(lap.getEndIndex() >= lap.getStartIndex());
			assertNotNull(lap.getTotalElevationGain());
			assertTrue(lap.getTotalElevationGain() >= 0);
			return;
			
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(lap.getActivity());
			validateActivity(lap.getActivity(), lap.getActivity().getId(), lap.getActivity().getResourceState());
			assertNotNull(lap.getAthlete());
			AthleteServicesImplTest.validateAthlete(lap.getAthlete(),lap.getAthlete().getId(),lap.getAthlete().getResourceState());
			if (lap.getAverageCadence() != null) {
				assertTrue(lap.getAverageCadence() >= 0);
			}
			if (lap.getAverageHeartrate() != null) { 
				assertTrue(lap.getAverageHeartrate() >= 0);
				assertNotNull(lap.getMaxHeartrate());
				assertTrue(lap.getMaxHeartrate() >= lap.getAverageHeartrate());
			}
			if (lap.getMaxHeartrate() != null) {
				assertNotNull(lap.getAverageHeartrate());
			}
			assertNotNull(lap.getAverageSpeed());
			assertTrue(lap.getAverageSpeed() >= 0);
			if (lap.getAverageWatts() != null) {
				assertTrue(lap.getAverageWatts() >= 0);
				assertNotNull(lap.getDeviceWatts());
			}
			assertNotNull(lap.getDistance());
			assertTrue(lap.getDistance() >= 0);
			assertNotNull(lap.getElapsedTime());
			assertTrue(lap.getElapsedTime() >= 0);
			assertNotNull(lap.getEndIndex());
			assertNotNull(lap.getLapIndex());
			assertNotNull(lap.getMaxSpeed());
			assertTrue(lap.getMaxSpeed() >= lap.getAverageSpeed());
			assertNotNull(lap.getMovingTime());
			assertTrue(lap.getMovingTime() >= 0);
			assertNotNull(lap.getName());
			assertNotNull(lap.getStartDate());
			assertNotNull(lap.getStartDateLocal());
			assertNotNull(lap.getStartIndex());
			assertTrue(lap.getEndIndex() >= lap.getStartIndex());
			assertNotNull(lap.getTotalElevationGain());
			assertTrue(lap.getTotalElevationGain() >= 0);
			return;
			
		}
		if (state == StravaResourceState.META) {
			assertNull(lap.getActivity());
			assertNull(lap.getAthlete());
			assertNull(lap.getAverageCadence());
			assertNull(lap.getAverageHeartrate());
			assertNull(lap.getMaxHeartrate());
			assertNull(lap.getAverageSpeed());
			assertNull(lap.getAverageWatts());
			assertNull(lap.getDeviceWatts());
			assertNull(lap.getDistance());
			assertNull(lap.getElapsedTime());
			assertNull(lap.getEndIndex());
			assertNull(lap.getLapIndex());
			assertNull(lap.getMaxSpeed());
			assertNull(lap.getMovingTime());
			assertNull(lap.getName());
			assertNull(lap.getStartDate());
			assertNull(lap.getStartDateLocal());
			assertNull(lap.getStartIndex());
			assertNull(lap.getTotalElevationGain());
			return;
			
		}
	}
	
	public static void validateActivityZone(StravaActivityZone zone, StravaResourceState state) {
		assertNotNull(zone);
		assertEquals(state, zone.getResourceState());
		
		if (state == StravaResourceState.DETAILED) {
			// Optional assertNotNull(zone.getCustomZones());
			assertNotNull(zone.getDistributionBuckets());
			for (StravaActivityZoneDistributionBucket bucket : zone.getDistributionBuckets()) {
				validateBucket(bucket);
			}
			if (zone.getType() == StravaActivityZoneType.HEARTRATE) {
				if (zone.getMax() != null) {
					assertTrue(zone.getMax() >= 0);
				}
			} else {
				assertNull(zone.getMax());
			}
			if (zone.getPoints() != null) {
				assertTrue(zone.getPoints() >= 0);
			}
			if (zone.getScore() != null) {
				assertTrue(zone.getScore() >= 0);
			}
			assertNotNull(zone.getType());
			assertFalse(zone.getType() == StravaActivityZoneType.UNKNOWN);
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			// Optional assertNotNull(zone.getCustomZones());
			assertNotNull(zone.getDistributionBuckets());
			for (StravaActivityZoneDistributionBucket bucket : zone.getDistributionBuckets()) {
				validateBucket(bucket);
			}
			if (zone.getType() == StravaActivityZoneType.HEARTRATE) {
				if (zone.getMax() != null) {
					assertTrue(zone.getMax() >= 0);
				}
			} else {
				assertNull(zone.getMax());
			}
			if (zone.getPoints() != null) {
				assertTrue(zone.getPoints() >= 0);
			}
			if (zone.getScore() != null) {
				assertTrue(zone.getScore() >= 0);
			}
			assertNotNull(zone.getType());
			assertFalse(zone.getType() == StravaActivityZoneType.UNKNOWN);
			return;
		}
		if (state == StravaResourceState.META) {
			assertNull(zone.getCustomZones());
			assertNull(zone.getDistributionBuckets());
			assertNull(zone.getMax());
			assertNull(zone.getPoints());
			assertNull(zone.getScore());
			assertNull(zone.getSensorBased());
			assertNull(zone.getType());
			return;
		}
		fail("unexpected state " + state + " for activity zone " + zone);
	}
	
	public static void validateBucket(StravaActivityZoneDistributionBucket bucket) {
		assertNotNull(bucket);
		assertNotNull(bucket.getMax());
		assertNotNull(bucket.getMin());
		assertNotNull(bucket.getTime());
	}
}
