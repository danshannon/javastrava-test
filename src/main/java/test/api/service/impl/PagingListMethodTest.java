package test.api.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.Paging;

import org.junit.Test;

/**
 * <p>
 * Utility test class used for testing all the service method implementations that return lists
 * </p>
 *
 * @author Dan Shannon
 *
 * @param <T> Class of objects contained in the lists returned by the method being tested
 * @param <U> Class of the object's identifier (mostly they're Integer, but some are Long or even String)
 */
public abstract class PagingListMethodTest<T,U> {
	/**
	 * <p>
	 * Test paging (page size only)
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testPageSize() {
		// Get a list with only one entry
		final List<T> list = callback().getList(new Paging(1,1));
		assertNotNull(list);
		assertEquals(1,list.size());

		// Validate all the entries in the list
		validateList(list);
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
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testPageNumberAndSize() {
		final List<T> bothPages = callback().getList(new Paging(1,2));
		assertNotNull(bothPages);
		assertEquals(2,bothPages.size());
		validateList(bothPages);
		final List<T> firstPage = callback().getList(new Paging(1,1));
		assertNotNull(firstPage);
		assertEquals(1,firstPage.size());
		validateList(firstPage);
		final List<T> secondPage = callback().getList(new Paging(2,1));
		assertNotNull(secondPage);
		assertEquals(1,secondPage.size());
		validateList(secondPage);

		// The first entry in bothPages should be the same as the first entry in firstPage
		assertEquals(bothPages.get(0),firstPage.get(0));

		// The second entry in bothPages should be the same as the first entry in secondPage
		assertEquals(bothPages.get(1),secondPage.get(0));

	}

	@Test
	public void testPageSizeTooLargeForStrava() {
		// Get a list with too many entries for Strava to cope with in a single paging instruction
		final List<T> list = callback().getList(new Paging(2,201));
		assertNotNull(list);
		assertTrue(list.size() <= 201);

		// Validate all the entries in the list
		validateList(list);
	}

	@Test
	public void testPagingIgnoreFirstN() {
		final List<T> bigList = callback().getList(new Paging(1,2,0,0));
		assertNotNull(bigList);
		assertEquals(2,bigList.size());

		final List<T> list = callback().getList(new Paging(1,2,1,0));
		assertNotNull(list);
		assertEquals(1,list.size());

		// The first entry in the list should be the second entry in bigList
		assertEquals(bigList.get(1),list.get(0));

		// Validate all the entries in the list
		validateList(bigList);
	}

	@Test
	public void testPagingIgnoreLastN() {
		final List<T> bigList = callback().getList(new Paging(1,2,0,0));
		assertNotNull(bigList);
		assertEquals(2,bigList.size());

		final List<T> list = callback().getList(new Paging(1,2,0,1));
		assertNotNull(list);
		assertEquals(1,list.size());

		// The first entry in the list should be the first entry in bigList
		assertEquals(bigList.get(0),list.get(0));

		// Validate all the entries in the list
		validateList(bigList);
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
	public void testPagingOutOfRangeLow() {
		try {
			callback().getList(new Paging(-1,-1));
		} catch (final IllegalArgumentException e) {
			// This is the expected behaviour
			return;
		}
		fail("Allowed paging instruction for page -1 of size -1!");
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
	public void testPagingOutOfRangeHigh() {
		// Get the 200,000,000th entry in the list - this is pretty unlikely to return anything!
		final List<T> list = callback().getList(new Paging(1000000,200));

		assertNotNull(list);
		assertEquals(0,list.size());
	}

	protected abstract void validate(final T object, final U id, final StravaResourceState state);

	protected abstract void validate(final T object);

	protected void validateList(final List<T> list) {
		for (final T entry : list) {
			assertNotNull(entry);
			validate(entry);
		}
	}

	protected abstract ListCallback<T> callback();

}
