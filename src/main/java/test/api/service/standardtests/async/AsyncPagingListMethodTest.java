package test.api.service.standardtests.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import javastrava.api.v3.model.StravaEntity;
import javastrava.util.Paging;
import test.api.service.standardtests.callbacks.AsyncPagingListCallback;
import test.api.service.standardtests.spec.PagingListMethodTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

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
public abstract class AsyncPagingListMethodTest<T extends StravaEntity, U> extends AsyncListMethodTest<T, U>
		implements PagingListMethodTests {
	protected abstract AsyncPagingListCallback<T, U> pagingLister();

	@Override
	protected abstract U idValidWithEntries();

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPageNumberAndSize()
	 */
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<T> bothPages = pagingLister().getList(TestUtils.strava(), new Paging(1, 2), idValidWithEntries()).get();
			assertNotNull(bothPages);
			assertEquals(2, bothPages.size());
			validateList(bothPages);
			final List<T> firstPage = pagingLister().getList(TestUtils.strava(), new Paging(1, 1), idValidWithEntries()).get();
			assertNotNull(firstPage);
			assertEquals(1, firstPage.size());
			validateList(firstPage);
			final List<T> secondPage = pagingLister().getList(TestUtils.strava(), new Paging(2, 1), idValidWithEntries()).get();
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
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPageSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with only one entry
			final List<T> list = pagingLister().getList(TestUtils.strava(), new Paging(1, 1), idValidWithEntries()).get();
			assertNotNull(list);
			assertEquals(1, list.size());

			// Validate all the entries in the list
			validateList(list);
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPageSizeTooLargeForStrava()
	 */
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPageSizeTooLargeForStrava() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with too many entries for Strava to cope with in a
			// single paging instruction
			final List<T> list = pagingLister().getList(TestUtils.strava(), new Paging(2, 201), idValidWithEntries()).get();
			assertNotNull(list);
			assertTrue(list.size() <= 201);

			// Validate all the entries in the list
			validateList(list);
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPagingIgnoreFirstN()
	 */
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPagingIgnoreFirstN() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<T> bigList = pagingLister().getList(TestUtils.strava(), new Paging(1, 2, 0, 0), idValidWithEntries()).get();
			assertNotNull(bigList);
			assertEquals(2, bigList.size());

			final List<T> list = pagingLister().getList(TestUtils.strava(), new Paging(1, 2, 1, 0), idValidWithEntries()).get();
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
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPagingIgnoreLastN() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<T> bigList = pagingLister().getList(TestUtils.strava(), new Paging(1, 2, 0, 0), idValidWithEntries()).get();
			assertNotNull(bigList);
			assertEquals(2, bigList.size());

			final List<T> list = pagingLister().getList(TestUtils.strava(), new Paging(1, 2, 0, 1), idValidWithEntries()).get();
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
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPagingOutOfRangeHigh() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the 200,000,000th entry in the list - this is pretty unlikely
			// to return anything!
			final List<T> list = pagingLister().getList(TestUtils.strava(), new Paging(1000000, 200), idValidWithEntries()).get();

			assertNotNull(list);
			assertEquals(0, list.size());
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PagingListMethodTests#testPagingOutOfRangeLow()
	 */
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPagingOutOfRangeLow() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				pagingLister().getList(TestUtils.strava(), new Paging(-1, -1), idValidWithEntries()).get();
			} catch (final ExecutionException e) {
				if (e.getCause() instanceof IllegalArgumentException) {
					// This is the expected behaviour
					return;
				}
				throw e;
			}
			fail("Allowed paging instruction for page -1 of size -1!"); //$NON-NLS-1$
		});
	}

	@Override
	protected abstract void validate(final T object);

	@Override
	protected void validateList(final List<T> list) {
		for (final T entry : list) {
			assertNotNull(entry);
			validate(entry);
		}
	}

}
