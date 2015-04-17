package test.api.service;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.cache.impl.StravaCacheImpl;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class CacheTest {
	/**
	 * <p>
	 * Test that when clearing the cache, there are no remaining accessible
	 * entities in there
	 * </p>
	 *
	 * @throws Exception
	 *             For whatever reason
	 */
	@Test
	public void testCache_clear() throws Exception {
		final Token token = TestUtils.getValidToken();
		final StravaCacheImpl<StravaActivity, Integer> cache = new StravaCacheImpl<StravaActivity, Integer>(
				StravaActivity.class, token);
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that clearing the cache for one class doesn't clear all the other
	 * classes
	 * </p>
	 *
	 * @throws Exception
	 *             For whatever unexpected reason
	 */
	@Test
	public void testCache_clearDoesntClearOtherClasses() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that clearing one token's entire cache doesn't clear data from other
	 * tokens' caches
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_clearDoesntClearOtherTokens() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that putting two different object with the same id and class results
	 * in the second put overwriting the first
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_doublePutReplacesObject() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that asking for an item NOT in the cache returns null and does not
	 * throw an exception
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_getInvalid() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that asking for an object that IS in the cache returns the right
	 * object
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_getValid() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that putting an object in the cache means that it is actually there
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_put() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that putting an object, then clearing the cache, then getting it,
	 * returns null
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_putClearGet() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that putting null is safe and doesn't fall over
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_putNull() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that putting an object of one class, then attempting to get an
	 * object of another class with the same id returns null
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_putOneClassGetAnotherWithSameId() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that putting an object with one cache instance, then getting it with
	 * another cache instance associated with a different token, returns null
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_putWithOneInstanceGetWithAnotherDifferentToken() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that putting an object with one cache instance, then getting it with
	 * another instance associated with the same token, returns the object
	 * successfully
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_putWithOneInstanceGetWithAnotherSameToken() throws Exception {
		fail("Not yet implemented!");

	}

	/**
	 * <p>
	 * Test that revoking a token results in the cache being empty for that
	 * token
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testCache_revokeTokenEmptiesCache() throws Exception {
		fail("Not yet implemented!");

	}

}
