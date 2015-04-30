package test.api.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.StravaConfig;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.rest.util.ArrayCallback;
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
public abstract class APIListTest<T, U> extends APITest<T> {
	/**
	 * Callback code for a test class to execute a non-paged request (used by the list tests)
	 */
	protected TestListArrayCallback<T, U> listCallback;

	/**
	 * Callback code for a test class to execute a request for a paged list (used by the paging tests)
	 */
	protected ArrayCallback<T> pagingCallback;

	/**
	 * <p>
	 * Not all list methods on the API support paging; so if this value is set to <code>true</code> then the paging parameter tests will not be run.
	 * </p>
	 */
	protected boolean suppressPagingTests = false;

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
	 * In some cases {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer) listAuthenticatedAthleteActivities}) this doesn't make
	 * sense, so if {@link #invalidId()} returns <code>null</code> then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void list_invalidParent() throws Exception {
		// If id is null, then don't run the test
		if (invalidId() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			try {
				this.listCallback.run(api(), invalidId());
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned a list of objects for an invalid parent id");
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent flagged by the authenticated athlete as PRIVATE works, if the access token has
	 * view_private scope
	 * </p>
	 *
	 * <p>
	 * In some cases this doesn't make sense because the parent can't be flagged as private (such as
	 * {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer) listAuthenticatedAthleteActivities}). So if {@link #privateId()}
	 * returns <code>null</code>, then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void list_private() throws Exception {
		// If the id is null, then don't run the test
		if (privateId() == null) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			final T[] results = this.listCallback.run(apiWithViewPrivate(), privateId());
			assertNotNull(results);
			assertNotEquals(0, results.length);
			validateArray(results);
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent flagged as private and not owned by the authenticated user throws a
	 * {@link UnauthorizedException}, even if the access token has view_private scope
	 * </p>
	 *
	 * <p>
	 * In some cases this doesn't make sense because the parent be owned by another athlete (such as
	 * {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer) listAuthenticatedAthleteActivities}). So if
	 * {@link #privateIdBelongsToOtherUser()} returns <code>null</code>, then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void list_privateBelongsToOtherUser() throws Exception {
		// If the id is null, then don't run the test
		if (privateIdBelongsToOtherUser() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			try {
				this.listCallback.run(apiWithViewPrivate(), privateIdBelongsToOtherUser());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned a list of objects for an private parent id which belongs to another user");
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent flagged by the authenticated athlete as PRIVATE throws an {@link UnauthorizedException},
	 * if the access token does not have view_private scope
	 * </p>
	 *
	 * <p>
	 * In some cases this doesn't make sense because the parent can't be flagged as private (such as
	 * {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer) listAuthenticatedAthleteActivities}). So if {@link #privateId()}
	 * returns <code>null</code>, then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void list_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// If the id is null, then don't run the test
				if (privateId() == null) {
					return;
				}

				try {
					this.listCallback.run(api(), privateId());
				} catch (final UnauthorizedException e) {
					// Expected
					return;
				}
				fail("Returned a list of objects for an private parent id which belongs to the authenticated user");
			});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent owned by another user works
	 * </p>
	 *
	 * <p>
	 * In some cases this doesn't make sense because the parent can't be owned by another user (such as
	 * {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer) listAuthenticatedAthleteActivities}). So if {@link #privateId()}
	 * returns <code>null</code>, then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void list_validBelongsToOtherUser() throws Exception {
		// If the id is null, then don't run the test
		if (validIdBelongsToOtherUser() == null) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			final T[] results = this.listCallback.run(api(), validIdBelongsToOtherUser());
			assertNotNull(results);
			assertNotEquals(0, results.length);
			validateArray(results);
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent owned by the authenticated user returns a list of objects
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void list_validParent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final T[] results = this.listCallback.run(api(), validId());
			assertNotNull(results);
			assertNotEquals(0, results.length);
			validateArray(results);
		});
	}

	/**
	 * <p>
	 * Test that asking for a list of objects which belong to a parent which has no children (for example, an activity might not have any comments) returns an
	 * empty list, rather than <code>null</code>
	 * </p>
	 *
	 * <p>
	 * In some cases this doesn't make sense because the parent can't be owned by another user (such as
	 * {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer) listAuthenticatedAthleteActivities}). So if
	 * {@link #validIdNoChildren()} returns <code>null</code>, then the test is not run
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void list_validParentNoChildren() throws Exception {
		// If validIdNoChildren() is null, don't run
		if (validIdNoChildren() == null) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			final T[] results = this.listCallback.run(api(), validIdNoChildren());
			assertNotNull(results);
			assertEquals(0, results.length);
		});
	}

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
	 * Test paging (page number and page size).
	 * </p>
	 *
	 * <p>
	 * To test this we get 2 entities from the service, then ask for the first page only and check that it's the same as the first entity, then ask for the
	 * second page and check that it's the same as the second entity
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testPageNumberAndSize() throws Exception {
		if (this.suppressPagingTests) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			final T[] bothPages = this.pagingCallback.getArray(new Paging(1, 2));
			assertNotNull(bothPages);
			assertEquals(2, bothPages.length);
			validateArray(bothPages);
			final T[] firstPage = this.pagingCallback.getArray(new Paging(1, 1));
			assertNotNull(firstPage);
			assertEquals(1, firstPage.length);
			validateArray(firstPage);
			final T[] secondPage = this.pagingCallback.getArray(new Paging(2, 1));
			assertNotNull(secondPage);
			assertEquals(1, secondPage.length);
			validateArray(secondPage);

			// The first entry in bothPages should be the same as the first
			// entry in firstPage
				assertEquals(bothPages[0], firstPage[0]);

				// The second entry in bothPages should be the same as the first
				// entry in secondPage
				assertEquals(bothPages[1], secondPage[0]);

			});
	}

	/**
	 * <p>
	 * Test paging (page size only)
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testPageSize() throws Exception {
		if (this.suppressPagingTests) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			// Get a list with only one entry
				final T[] list = this.pagingCallback.getArray(new Paging(1, 1));
				assertNotNull(list);
				assertEquals(1, list.length);

				// Validate all the entries in the list
				validateArray(list);
			});
	}

	/**
	 * <p>
	 * Test paging parameters where page size exceeds the maximum size allowed by Strava; this should result in a {@link BadRequestException} being thrown by
	 * the API
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testPageSizeTooLargeForStrava() throws Exception {
		if (this.suppressPagingTests) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			// Get a list with too many entries for Strava to cope with in a
			// single paging instruction
				try {
					this.pagingCallback.getArray(new Paging(2, StravaConfig.MAX_PAGE_SIZE.intValue() + 1));
				} catch (final BadRequestException e) {
					// Expected
					return;
				}
				fail("Strava is coping with more elements per page than expected");
			});
	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too high)
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testPagingOutOfRangeHigh() throws Exception {
		if (this.suppressPagingTests) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			// Get the 200,000,000th entry in the list - this is pretty unlikely
			// to return anything!
				final T[] list = this.pagingCallback.getArray(new Paging(1000000, 200));

				assertNotNull(list);
				assertEquals(0, list.length);
			});
	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too low)
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testPagingOutOfRangeLow() throws Exception {
		if (this.suppressPagingTests) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			try {
				this.pagingCallback.getArray(new Paging(-1, -1));
			} catch (final BadRequestException e) {
				// This is the expected behaviour
				return;
			}
			fail("Allowed paging instruction for page -1 of size -1!");
		});
	}

	/**
	 * <p>
	 * Validate the contents of an array of objects returned by the API
	 * </p>
	 *
	 * @param list
	 */
	protected abstract void validateArray(final T[] list);

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
