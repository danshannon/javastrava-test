package test.api.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.StravaConfig;
import javastrava.util.Paging;
import test.api.rest.util.ArrayCallback;
import test.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 */
public abstract class APIListTest<T, U> extends APITest<T> {
	protected TestListArrayCallback<T, U> listCallback;

	protected ArrayCallback<T> pagingCallback;

	protected boolean suppressPagingTests = false;

	protected abstract U invalidId();

	@Test
	public void list_invalidParent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				this.listCallback.run(api(), invalidId());
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned a list of objects for an invalid parent id");
		} );
	}

	@Test
	public void list_private() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final T[] results = this.listCallback.run(apiWithViewPrivate(), privateId());
			assertNotNull(results);
			assertNotEquals(0, results.length);
			validateList(results);
		} );
	}

	@Test
	public void list_privateBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				this.listCallback.run(apiWithViewPrivate(), privateIdBelongsToOtherUser());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned a list of objects for an private parent id which belongs to another user");
		} );
	}

	@Test
	public void list_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				this.listCallback.run(api(), privateId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned a list of objects for an private parent id which belongs to the authenticated user");
		} );
	}

	@Test
	public void list_validBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final T[] results = this.listCallback.run(api(), validIdBelongsToOtherUser());
			assertNotNull(results);
			assertNotEquals(0, results.length);
			validateList(results);
		} );
	}

	@Test
	public void list_validParent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final T[] results = this.listCallback.run(api(), validId());
			assertNotNull(results);
			assertNotEquals(0, results.length);
			validateList(results);
		} );
	}

	@Test
	public void list_validParentNoChildren() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final T[] results = this.listCallback.run(api(), validIdNoChildren());
			assertNotNull(results);
			assertEquals(0, results.length);
		} );
	}

	protected abstract U privateId();

	protected abstract U privateIdBelongsToOtherUser();

	/**
	 * <p>
	 * Test paging (page number and page size).
	 * </p>
	 *
	 * <p>
	 * To test this we get 2 entities from the service, then ask for the first
	 * page only and check that it's the same as the first entity, then ask for
	 * the second page and check that it's the same as the second entity
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
			validateList(bothPages);
			final T[] firstPage = this.pagingCallback.getArray(new Paging(1, 1));
			assertNotNull(firstPage);
			assertEquals(1, firstPage.length);
			validateList(firstPage);
			final T[] secondPage = this.pagingCallback.getArray(new Paging(2, 1));
			assertNotNull(secondPage);
			assertEquals(1, secondPage.length);
			validateList(secondPage);

			// The first entry in bothPages should be the same as the first
			// entry in firstPage
			assertEquals(bothPages[0], firstPage[0]);

			// The second entry in bothPages should be the same as the first
			// entry in secondPage
			assertEquals(bothPages[1], secondPage[0]);

		} );
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
			validateList(list);
		} );
	}

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
		} );
	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out
	 * of range - too high)
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
		} );
	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out
	 * of range - too low)
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
		} );
	}

	protected abstract void validateList(final T[] list);

	protected abstract U validId();

	protected abstract U validIdBelongsToOtherUser();

	protected abstract U validIdNoChildren();

}
