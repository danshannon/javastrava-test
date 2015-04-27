package test.api.rest.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.StravaConfig;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.rest.AsyncAPITest;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Utility test class used for testing all the service method implementations that return lists
 * </p>
 *
 * @author Dan Shannon
 *
 * @param <T>
 *            Class of objects contained in the lists returned by the method being tested
 * @param <U>
 *            Class of the object's identifier (mostly they're Integer, but some are Long or even String)
 */
public abstract class PagingArrayMethodAsyncTest<T, U> extends AsyncAPITest<T, U> {
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
		RateLimitedTestRunner.run(() -> {
			// Get a list with only one entry
			final T[] list = callback().getArray(new Paging(1, 1));
			assertNotNull(list);
			assertEquals(1, list.length);

			// Validate all the entries in the list
			validateList(list);
		});
	}

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
		RateLimitedTestRunner.run(() -> {
			final T[] bothPages = callback().getArray(new Paging(1, 2));
			assertNotNull(bothPages);
			assertEquals(2, bothPages.length);
			validateList(bothPages);
			final T[] firstPage = callback().getArray(new Paging(1, 1));
			assertNotNull(firstPage);
			assertEquals(1, firstPage.length);
			validateList(firstPage);
			final T[] secondPage = callback().getArray(new Paging(2, 1));
			assertNotNull(secondPage);
			assertEquals(1, secondPage.length);
			validateList(secondPage);

			// The first entry in bothPages should be the same as the first entry in firstPage
			assertEquals(bothPages[0], firstPage[0]);

			// The second entry in bothPages should be the same as the first entry in secondPage
			assertEquals(bothPages[1], secondPage[0]);

		});
	}

	@Test
	public void testPageSizeTooLargeForStrava() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with too many entries for Strava to cope with in a single paging instruction
				try {
					callback().getArray(new Paging(2, StravaConfig.MAX_PAGE_SIZE.intValue() + 1));
				} catch (final BadRequestException e) {
					// Expected
					return;
				}
				fail("Strava is coping with more elements per page than expected");
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
		RateLimitedTestRunner.run(() -> {
			try {
				callback().getArray(new Paging(-1, -1));
			} catch (final BadRequestException e) {
				// This is the expected behaviour
				return;
			}
			fail("Allowed paging instruction for page -1 of size -1!");
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
		RateLimitedTestRunner.run(() -> {
			// Get the 200,000,000th entry in the list - this is pretty unlikely to return anything!
			final T[] list = callback().getArray(new Paging(1000000, 200));

			assertNotNull(list);
			assertEquals(0, list.length);
		});
	}

	protected abstract void validate(final T object, final U id, final StravaResourceState state);

	protected abstract void validate(final T object);

	protected void validateList(final T[] bigList) {
		for (final T entry : bigList) {
			assertNotNull(entry);
			validate(entry);
		}
	}

	protected abstract ArrayCallback<T> callback() throws Exception;

}
