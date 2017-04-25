package test.service.standardtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.model.StravaEntity;
import javastrava.util.Paging;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.spec.PagingListMethodTests;
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
 *            Class of the object's parent's identifier (mostly they too are Integer)
 */
public abstract class PagingListMethodTest<T extends StravaEntity, U> extends ListMethodTest<T, U> implements PagingListMethodTests {

	/**
	 * @return The callback used to get the paging list from Strava
	 */
	protected abstract PagingListCallback<T, U> pagingLister();

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
	 *             if test fails in an unexpected wat
	 */
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<T> bothPages = pagingLister().getList(TestUtils.strava(), new Paging(1, 2), idValidWithEntries());
			assertNotNull(bothPages);
			assertEquals(2, bothPages.size());
			validateList(bothPages);
			final List<T> firstPage = pagingLister().getList(TestUtils.strava(), new Paging(1, 1), idValidWithEntries());
			assertNotNull(firstPage);
			assertEquals(1, firstPage.size());
			validateList(firstPage);
			final List<T> secondPage = pagingLister().getList(TestUtils.strava(), new Paging(2, 1), idValidWithEntries());
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
	 * <p>
	 * Test paging (page size only)
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPageSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with only one entry
			final List<T> list = pagingLister().getList(TestUtils.strava(), new Paging(1, 1), idValidWithEntries());
			assertNotNull(list);
			assertEquals(1, list.size());

			// Validate all the entries in the list
			validateList(list);
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPageSizeTooLargeForStrava() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with too many entries for Strava to cope with in a
			// single paging instruction
			final List<T> list = pagingLister().getList(TestUtils.strava(), new Paging(2, 201), idValidWithEntries());
			assertNotNull(list);
			assertTrue(list.size() <= 201);

			// Validate all the entries in the list
			validateList(list);
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPagingIgnoreFirstN() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<T> bigList = pagingLister().getList(TestUtils.strava(), new Paging(1, 2, 0, 0), idValidWithEntries());
			assertNotNull(bigList);
			assertEquals(2, bigList.size());

			final List<T> list = pagingLister().getList(TestUtils.strava(), new Paging(1, 2, 1, 0), idValidWithEntries());
			assertNotNull(list);
			assertEquals(1, list.size());

			// The first entry in the list should be the second entry in bigList
			assertEquals(bigList.get(1), list.get(0));

			// Validate all the entries in the list
			validateList(bigList);
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPagingIgnoreLastN() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<T> bigList = pagingLister().getList(TestUtils.strava(), new Paging(1, 2, 0, 0), idValidWithEntries());
			assertNotNull(bigList);
			assertEquals(2, bigList.size());

			final List<T> list = pagingLister().getList(TestUtils.strava(), new Paging(1, 2, 0, 1), idValidWithEntries());
			assertNotNull(list);
			assertEquals(1, list.size());

			// The first entry in the list should be the first entry in bigList
			assertEquals(bigList.get(0), list.get(0));

			// Validate all the entries in the list
			validateList(bigList);
		});
	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too high)
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPagingOutOfRangeHigh() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the 200,000,000th entry in the list - this is pretty unlikely
			// to return anything!
			final List<T> list = pagingLister().getList(TestUtils.strava(), new Paging(1000000, 200), idValidWithEntries());

			assertNotNull(list);
			assertEquals(0, list.size());
		});
	}

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too low)
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPagingOutOfRangeLow() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				pagingLister().getList(TestUtils.strava(), new Paging(-1, -1), idValidWithEntries());
			} catch (final IllegalArgumentException e) {
				// This is the expected behaviour
				return;
			}
			fail("Allowed paging instruction for page -1 of size -1!"); //$NON-NLS-1$
		});
	}

}
