package test.api.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.StravaConfig;
import javastrava.util.Paging;
import test.api.rest.util.ArrayCallback;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Tests for paging list methods
 * </p>
 *
 * @author Dan Shannon
 *
 * @param <T>
 *            The type of Strava entity being returned
 * @param <U>
 *            The class of the parent identifier used
 */
public abstract class APIPagingListTest<T extends StravaEntity, U> extends APIListTest<T, U> {
	/**
	 * Callback code for a test class to execute a request for a paged list (used by the paging tests)
	 *
	 * @return The callback to be used for paging requests
	 */
	protected abstract ArrayCallback<T> pagingCallback();

	/**
	 * <p>
	 * Test paging (page number and page size).
	 * </p>
	 *
	 * <p>
	 * To test this we get 2 entities from the service, then ask for the first page only and check that it's the same as the first entity, then ask for the second page and check that it's the same as
	 * the second entity
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final T[] bothPages = this.pagingCallback().getArray(new Paging(1, 2));
			assertNotNull(bothPages);
			assertEquals(2, bothPages.length);
			validateArray(bothPages);
			final T[] firstPage = this.pagingCallback().getArray(new Paging(1, 1));
			assertNotNull(firstPage);
			assertEquals(1, firstPage.length);
			validateArray(firstPage);
			final T[] secondPage = this.pagingCallback().getArray(new Paging(2, 1));
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
	 *             if the test fails in an unexpected way
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testPageSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with only one entry
			final T[] list = this.pagingCallback().getArray(new Paging(1, 1));
			assertNotNull(list);
			assertEquals(1, list.length);

			// Validate all the entries in the list
			validateArray(list);
		});
	}

	/**
	 * <p>
	 * Test paging parameters where page size exceeds the maximum size allowed by Strava; this should result in a {@link BadRequestException} being thrown by the API
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testPageSizeTooLargeForStrava() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with too many entries for Strava to cope with in a
			// single paging instruction
			try {
				this.pagingCallback().getArray(new Paging(2, StravaConfig.MAX_PAGE_SIZE.intValue() + 1));
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Strava is coping with more elements per page than expected"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too high)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testPagingOutOfRangeHigh() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the 200,000,000th entry in the list - this is pretty unlikely
			// to return anything!
			final T[] list = this.pagingCallback().getArray(new Paging(1000000, 200));

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
	 *             if the test fails in an unexpected way
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testPagingOutOfRangeLow() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				this.pagingCallback().getArray(new Paging(-1, -1));
			} catch (final BadRequestException e) {
				// This is the expected behaviour
				return;
			}
			fail("Allowed paging instruction for page -1 of size -1!"); //$NON-NLS-1$
		});
	}

}
