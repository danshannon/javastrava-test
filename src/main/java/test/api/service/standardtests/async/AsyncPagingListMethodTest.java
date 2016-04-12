package test.api.service.standardtests.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.service.StravaTest;
import test.api.service.standardtests.callbacks.AsyncListCallback;
import test.api.service.standardtests.spec.PagingListMethodTests;
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
public abstract class AsyncPagingListMethodTest<T, U> extends StravaTest<T, U> implements PagingListMethodTests {
	protected abstract AsyncListCallback<T> deleter();

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPageNumberAndSize()
	 */
	@Override
	@Test
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<T> bothPages = deleter().getList(new Paging(1, 2)).get();
			assertNotNull(bothPages);
			assertEquals(2, bothPages.size());
			validateList(bothPages);
			final List<T> firstPage = deleter().getList(new Paging(1, 1)).get();
			assertNotNull(firstPage);
			assertEquals(1, firstPage.size());
			validateList(firstPage);
			final List<T> secondPage = deleter().getList(new Paging(2, 1)).get();
			assertNotNull(secondPage);
			assertEquals(1, secondPage.size());
			validateList(secondPage);

			// The first entry in bothPages should be the same as the first
			// entry in firstPage
			assertEquals(bothPages.get(0), firstPage.get(0));

			// The second entry in bothPages should be the same as the first
			// entry in secondPage
			assertEquals(bothPages.get(1), secondPage.get(0));

		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPageSize()
	 */
	@Override
	@Test
	public void testPageSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with only one entry
			final List<T> list = deleter().getList(new Paging(1, 1)).get();
			assertNotNull(list);
			assertEquals(1, list.size());

			// Validate all the entries in the list
			validateList(list);
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPageSizeTooLargeForStrava()
	 */
	@Override
	@Test
	public void testPageSizeTooLargeForStrava() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with too many entries for Strava to cope with in a
			// single paging instruction
			final List<T> list = deleter().getList(new Paging(2, 201)).get();
			assertNotNull(list);
			assertTrue(list.size() <= 201);

			// Validate all the entries in the list
			validateList(list);
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPagingIgnoreFirstN()
	 */
	@Override
	@Test
	public void testPagingIgnoreFirstN() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<T> bigList = deleter().getList(new Paging(1, 2, 0, 0)).get();
			assertNotNull(bigList);
			assertEquals(2, bigList.size());

			final List<T> list = deleter().getList(new Paging(1, 2, 1, 0)).get();
			assertNotNull(list);
			assertEquals(1, list.size());

			// The first entry in the list should be the second entry in bigList
			assertEquals(bigList.get(1), list.get(0));

			// Validate all the entries in the list
			validateList(bigList);
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPagingIgnoreLastN()
	 */
	@Override
	@Test
	public void testPagingIgnoreLastN() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<T> bigList = deleter().getList(new Paging(1, 2, 0, 0)).get();
			assertNotNull(bigList);
			assertEquals(2, bigList.size());

			final List<T> list = deleter().getList(new Paging(1, 2, 0, 1)).get();
			assertNotNull(list);
			assertEquals(1, list.size());

			// The first entry in the list should be the first entry in bigList
			assertEquals(bigList.get(0), list.get(0));

			// Validate all the entries in the list
			validateList(bigList);
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPagingOutOfRangeHigh()
	 */
	@Override
	@Test
	public void testPagingOutOfRangeHigh() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the 200,000,000th entry in the list - this is pretty unlikely
			// to return anything!
			final List<T> list = deleter().getList(new Paging(1000000, 200)).get();

			assertNotNull(list);
			assertEquals(0, list.size());
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPagingOutOfRangeLow()
	 */
	@Override
	@Test
	public void testPagingOutOfRangeLow() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				deleter().getList(new Paging(-1, -1)).get();
			} catch (final ExecutionException e) {
				if (e.getCause() instanceof IllegalArgumentException) {
					// This is the expected behaviour
				return;
				}
				throw e;
			}
			fail("Allowed paging instruction for page -1 of size -1!");
		})  ;
	}

	@Override
	protected abstract void validate(final T object);

	@Override
	protected abstract void validate(final T object, final U id, final StravaResourceState state);

	@Override
	protected void validateList(final List<T> list) {
		for (final T entry : list) {
			assertNotNull(entry);
			validate(entry);
		}
	}

}
