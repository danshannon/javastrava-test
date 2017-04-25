package test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;

import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaEntity;
import javastrava.model.StravaLap;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import test.api.callback.APIListCallback;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Standard tests for the API's list* methods
 * </p>
 *
 * <p>
 * To create a test for a method, subclass this class and implement the abstract methods.
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            Class of object which is returned in the lists
 * @param <U>
 *            Class of the object's id
 */
public abstract class APIListTest<T extends StravaEntity, U> extends APITest<T> {
	/**
	 * <p>
	 * In some cases attempting to list items such as {@link StravaLap laps} on an item belonging to another user returns a 401 Unauthorized error. Where this is the case the test class should set
	 * this value to <code>true</code>
	 * </p>
	 */
	protected boolean listOtherReturns401Unauthorised = false;

	/**
	 * @return Returns an id for which there is no parent object in existence
	 */
	protected abstract U invalidId();

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent that doesn't exist throws a 404 {@link NotFoundException}
	 * </p>
	 *
	 * <p>
	 * In some cases {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer) listAuthenticatedAthleteActivities}) this doesn't make sense, so if {@link #invalidId()} returns
	 * <code>null</code> then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void list_invalidParent() throws Exception {
		// If id is null, then don't run the test
		assumeNotNull(invalidId());

		RateLimitedTestRunner.run(() -> {
			try {
				this.listCallback().list(api(), invalidId());
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned a list of objects for an invalid parent id: " + invalidId()); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent flagged by the authenticated athlete as PRIVATE works, if the access token has view_private scope
	 * </p>
	 *
	 * <p>
	 * In some cases this doesn't make sense because the parent can't be flagged as private (such as {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer)
	 * listAuthenticatedAthleteActivities}). So if {@link #privateId()} returns <code>null</code>, then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void list_private() throws Exception {
		// If the id is null, then don't run the test
		assumeNotNull(privateId());

		RateLimitedTestRunner.run(() -> {
			T[] results = null;
			try {
				results = this.listCallback().list(apiWithViewPrivate(), privateId());
			} catch (final NotFoundException e) {
				fail("Attempt with view_private scope to list children of a private parent with id " + privateId() + " failed with a NotFoundException"); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (final UnauthorizedException e) {
				fail("Attempt with view_private scope to list children of a private parent with id " + privateId() + " failed with an UnauthorizedException"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			assertNotNull(results);
			validateArray(results);
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent flagged as private and not owned by the authenticated user throws a {@link UnauthorizedException}, even if the access token has
	 * view_private scope
	 * </p>
	 *
	 * <p>
	 * In some cases this doesn't make sense because the parent be owned by another athlete (such as {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer)
	 * listAuthenticatedAthleteActivities}). So if {@link #privateIdBelongsToOtherUser()} returns <code>null</code>, then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void list_privateBelongsToOtherUser() throws Exception {
		// If the id is null, then don't run the test
		assumeNotNull(privateIdBelongsToOtherUser());

		RateLimitedTestRunner.run(() -> {
			try {
				this.listCallback().list(apiWithViewPrivate(), privateIdBelongsToOtherUser());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			} catch (final NotFoundException e) {
				fail("Got a NotFoundException when requesting list of objects for private parent which belongs to another user, parent id = " + privateIdBelongsToOtherUser()); //$NON-NLS-1$
			}
			fail("Returned a list of objects for an private parent id which belongs to another user"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent flagged by the authenticated athlete as PRIVATE throws an {@link UnauthorizedException}, if the access token does not have
	 * view_private scope
	 * </p>
	 *
	 * <p>
	 * In some cases this doesn't make sense because the parent can't be flagged as private (such as {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer)
	 * listAuthenticatedAthleteActivities}). So if {@link #privateId()} returns <code>null</code>, then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void list_privateWithoutViewPrivate() throws Exception {
		// If the id is null, then don't run the test
		assumeNotNull(privateId());

		RateLimitedTestRunner.run(() -> {
			T[] list;
			try {
				list = this.listCallback().list(api(), privateId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned a list of " + list.length + " objects for a private parent id (" + privateId() + ") which belongs to the authenticated user"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent owned by another user works
	 * </p>
	 *
	 * <p>
	 * In some cases this doesn't make sense because the parent can't be owned by another user (such as {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer)
	 * listAuthenticatedAthleteActivities}). So if {@link #privateId()} returns <code>null</code>, then the test is not run
	 * </p>
	 *
	 * <p>
	 * In other cases, the Strava API doesn't allow items that belong to an item that is owned by another user to be listed, returning a 401 Unauthorised error. If the
	 * {@link #listOtherReturns401Unauthorised} is <code>true</code> then we expect to get a {@link UnauthorizedException} thrown.
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void list_validBelongsToOtherUser() throws Exception {
		// If the id is null, then don't run the test
		assumeNotNull(validIdBelongsToOtherUser());

		RateLimitedTestRunner.run(() -> {
			T[] results = null;
			try {
				results = this.listCallback().list(api(), validIdBelongsToOtherUser());
			} catch (final UnauthorizedException e) {
				if (this.listOtherReturns401Unauthorised) {
					// Expected
					return;
				} else {
					throw e;
				}
			}
			if (this.listOtherReturns401Unauthorised) {
				// If we get here, we should have got a 401 returned, but we didn't
				fail("Listed items belonging to another user unexpectedly!"); //$NON-NLS-1$
			}
			assertNotNull(results);
			// assertNotEquals(0, results.length);
			validateArray(results);
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent owned by the authenticated user returns a list of objects
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void list_validParent() throws Exception {
		assumeNotNull(validId());

		RateLimitedTestRunner.run(() -> {
			final T[] results = this.listCallback().list(api(), validId());
			assertNotNull(results);
			assertNotEquals(0, results.length);
			validateArray(results);
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent which has no children (for example, an activity might not have any comments) returns an empty list, rather than <code>null</code>
	 * </p>
	 *
	 * <p>
	 * In some cases this doesn't make sense because the parent can't be owned by another user (such as {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer)
	 * listAuthenticatedAthleteActivities}). So if {@link #validIdNoChildren()} returns <code>null</code>, then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void list_validParentNoChildren() throws Exception {
		// If validIdNoChildren() is null, don't run
		assumeNotNull(validIdNoChildren());

		RateLimitedTestRunner.run(() -> {
			final T[] results = this.listCallback().list(api(), validIdNoChildren());
			assertNotNull(results);
			assertEquals(0, results.length);
		});
	}

	/**
	 * Callback code for a test class to execute a non-paged request (used by the list tests)
	 *
	 * @return The callback to be used for non-paging requests
	 */
	protected abstract APIListCallback<T, U> listCallback();

	/**
	 * @return The id of a parent object which is owned by the authenticated athlete and flagged as private
	 */
	protected abstract U privateId();

	/**
	 * @return The id of a parent object which is owned by another user and flagged as private
	 */
	protected abstract U privateIdBelongsToOtherUser();

	/**
	 * <p>
	 * Validate the contents of an array of objects returned by the API
	 * </p>
	 *
	 * @param list
	 *            List of objects to be validated
	 * @throws Exception
	 *             if the validation fails
	 */
	protected abstract void validateArray(final T[] list) throws Exception;

	/**
	 * @return The id of a valid parent object which belongs to the authenticated user.
	 */
	protected abstract U validId();

	/**
	 * @return The id of a valid parent object which belongs to another user
	 */
	protected abstract U validIdBelongsToOtherUser();

	/**
	 * @return The id of a valid parent object which has no children (e.g. an activity which has no comments)
	 */
	protected abstract U validIdNoChildren();

}
