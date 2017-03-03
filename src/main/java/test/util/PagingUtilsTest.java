package test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.junit.Test;

import javastrava.config.StravaConfig;
import javastrava.util.Paging;
import javastrava.util.PagingUtils;

/**
 * <p>
 * Tests for {@link PagingUtils}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class PagingUtilsTest {
	private final Random random = new Random(System.currentTimeMillis());

	/**
	 * @return
	 */
	private List<String> getRandomList() {
		final List<String> result = new ArrayList<String>();
		final int size = this.random.nextInt(998) + 2; // i.e. must be at least 2
		final Fairy fairy = Fairy.create();
		for (int i = 1; i <= size; i++) {
			result.add(fairy.textProducer().sentence());
		}
		return result;
	}

	/**
	 * Test default values
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testConvertToStravaPaging_defaultValueTest() {
		final List<Paging> pagingList = PagingUtils.convertToStravaPaging(new Paging(0, 0));
		assertNotNull(pagingList);
		assertEquals(1, pagingList.size());
		final Paging paging = pagingList.get(0);
		assertEquals(1, paging.getPage().intValue());
		assertEquals(StravaConfig.DEFAULT_PAGE_SIZE, paging.getPageSize());
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testConvertToStravaPaging_ignoreFirstNLargerThanMaxPageSize() {
		// One page of MAX+2 elements, ignoring the first MAX+1
		List<Paging> pagingList = PagingUtils.convertToStravaPaging(new Paging(1, StravaConfig.MAX_PAGE_SIZE + 2, StravaConfig.MAX_PAGE_SIZE + 1, 0));
		assertNotNull(pagingList);
		assertEquals(1, pagingList.size());
		Paging paging = pagingList.get(0);
		Paging expected = new Paging(2, StravaConfig.MAX_PAGE_SIZE, 1, StravaConfig.MAX_PAGE_SIZE - 2);
		assertEquals(expected, paging);

		// Second page of MAX+2 elements, ignoring the first MAX+1
		pagingList = PagingUtils.convertToStravaPaging(new Paging(2, StravaConfig.MAX_PAGE_SIZE + 2, StravaConfig.MAX_PAGE_SIZE + 1, 0));
		assertNotNull(pagingList);
		assertEquals(1, pagingList.size());
		paging = pagingList.get(0);
		expected = new Paging(3, StravaConfig.MAX_PAGE_SIZE, 3, StravaConfig.MAX_PAGE_SIZE - 4);
		assertEquals(expected, paging);
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testConvertToStravaPaging_ignoreLastNLargerThanMaxPageSize() {
		// One page of MAX+2 elements, ignoring the last MAX+1
		// Should return only the first element
		final List<Paging> pagingList = PagingUtils.convertToStravaPaging(new Paging(1, StravaConfig.MAX_PAGE_SIZE + 2, 0, StravaConfig.MAX_PAGE_SIZE + 1));
		assertNotNull(pagingList);
		assertEquals(1, pagingList.size());
		final Paging paging = pagingList.get(0);
		assertEquals(1, paging.getPage().intValue());
		assertEquals(1, paging.getPageSize().intValue());
		assertEquals(0, paging.getIgnoreLastN());
		assertEquals(0, paging.getIgnoreFirstN());

	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testConvertToStravaPaging_large() {
		final List<Paging> paging = PagingUtils.convertToStravaPaging(new Paging(2, 201));
		assertEquals("Should return 2 paging instructions", 2, paging.size()); //$NON-NLS-1$
		final Paging first = paging.get(0);
		final Paging second = paging.get(1);
		assertEquals("First paging instruction should be page size " + StravaConfig.MAX_PAGE_SIZE, StravaConfig.MAX_PAGE_SIZE, //$NON-NLS-1$
				first.getPageSize());
		assertEquals("First paging instruction should be for page 2", 2, first.getPage().intValue()); //$NON-NLS-1$
		assertEquals("First paging instruction should ignore last 0 records", 0, first.getIgnoreLastN()); //$NON-NLS-1$
		assertEquals("Second paging instruction should be page size " + StravaConfig.MAX_PAGE_SIZE, StravaConfig.MAX_PAGE_SIZE, //$NON-NLS-1$
				second.getPageSize());
		assertEquals("Second paging instruction should be for page 3", 3, second.getPage().intValue()); //$NON-NLS-1$
		assertEquals("Second paging instruction should ignore last 198 records", 198, second.getIgnoreLastN()); //$NON-NLS-1$

	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testConvertToStravaPaging_sequence() {
		List<Paging> paging = PagingUtils.convertToStravaPaging(new Paging(1, StravaConfig.MAX_PAGE_SIZE));
		assertEquals(1, paging.size());
		paging = PagingUtils.convertToStravaPaging(new Paging(2, StravaConfig.MAX_PAGE_SIZE));
		assertEquals(1, paging.size());
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testConvertToStravaPaging_small() {
		final List<Paging> paging = PagingUtils.convertToStravaPaging(new Paging(7, 11));
		assertEquals("Should return one paging instruction", 1, paging.size()); //$NON-NLS-1$
		assertEquals("Paging instruction should return page number 7", 7, paging.get(0).getPage().intValue()); //$NON-NLS-1$
		assertEquals("Paging instruction should return page size 11", 11, paging.get(0).getPageSize().intValue()); //$NON-NLS-1$
		assertEquals("Paging instruction should ignore last 0", 0, paging.get(0).getIgnoreLastN()); //$NON-NLS-1$
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testIgnoreFirstN_invalidNegative() {
		final List<String> list = getRandomList();

		final int firstN = -1;
		try {
			PagingUtils.ignoreFirstN(list, firstN);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Can't remove a negative number of items from a list!"); //$NON-NLS-1$
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testIgnoreFirstN_invalidTooLarge() {
		List<String> list = getRandomList();
		list = PagingUtils.ignoreFirstN(list, list.size() + 1);
		assertNotNull(list);
		assertEquals(0, list.size());
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testIgnoreFirstN_nullList() {
		final List<String> list = null;
		final List<String> result = PagingUtils.ignoreFirstN(list, 10);
		assertNull(result);
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testIgnoreFirstN_valid() {
		// Create a list
		final List<String> list = getRandomList();

		// Remove a random number of elements from it
		final int size = list.size();
		final int firstN = this.random.nextInt(list.size() - 2) + 1; // i.e. between 1 and list.size() - 1
		final List<String> result = PagingUtils.ignoreFirstN(list, firstN);
		assertEquals(size - firstN, result.size());
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testIgnoreFirstN_zero() {
		List<String> list = getRandomList();
		final int size = list.size();

		list = PagingUtils.ignoreFirstN(list, 0);
		assertEquals(size, list.size());
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testIgnoreLastN_invalidNegative() {
		final List<String> list = getRandomList();

		final int lastN = -1;
		try {
			PagingUtils.ignoreLastN(list, lastN);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Can't remove a negative number of items from a list!"); //$NON-NLS-1$
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testIgnoreLastN_invalidTooLarge() {
		List<String> list = getRandomList();
		list = PagingUtils.ignoreFirstN(list, list.size() + 1);
		assertNotNull(list);
		assertEquals(0, list.size());
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testIgnoreLastN_nullList() {
		final List<String> list = null;
		final List<String> result = PagingUtils.ignoreLastN(list, 1);
		assertNull(result);
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testIgnoreLastN_valid() {
		// Create a list
		final List<String> list = getRandomList();

		// Remove a random number of elements from it
		final int size = list.size();
		final int lastN = this.random.nextInt(list.size() - 2) + 1; // i.e. between 1 and list.size() - 1
		final List<String> result = PagingUtils.ignoreLastN(list, lastN);
		assertEquals(size - lastN, result.size());
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testIgnoreLastN_zero() {
		List<String> list = getRandomList();
		final int size = list.size();

		list = PagingUtils.ignoreLastN(list, 0);
		assertEquals(size, list.size());
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testValidatePagingArguments_ignoreFirstNGreaterThanPageSize() {
		final Paging paging = new Paging(1, StravaConfig.MAX_PAGE_SIZE, StravaConfig.MAX_PAGE_SIZE + 1, 0);
		try {
			PagingUtils.validatePagingArguments(paging);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Succeeded in validating a paging instruction to ignore more elements than are on the page"); //$NON-NLS-1$
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testValidatePagingArguments_ignoreLastNGreaterThanPageSize() {
		final Paging paging = new Paging(1, StravaConfig.MAX_PAGE_SIZE, 0, StravaConfig.MAX_PAGE_SIZE + 1);
		try {
			PagingUtils.validatePagingArguments(paging);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Succeeded in validating a paging instruction to ignore more elements than are on the page"); //$NON-NLS-1$
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testValidatePagingArguments_negativePage() {
		final Paging paging = new Paging(-1, 0);
		try {
			PagingUtils.validatePagingArguments(paging);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Succeeded in validating a paging instruction for page -1"); //$NON-NLS-1$
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testValidatePagingArguments_negativePageSize() {
		final Paging paging = new Paging(1, -1);
		try {
			PagingUtils.validatePagingArguments(paging);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Succeeded in validating a paging instruction for page size -1"); //$NON-NLS-1$
	}

	/**
	 * Test conversion to Strava-format paging parameters
	 */
	@Test
	public void testValidatePagingArguments_nullPagingInstruction() {
		final Paging paging = null;
		PagingUtils.validatePagingArguments(paging);
	}

}
