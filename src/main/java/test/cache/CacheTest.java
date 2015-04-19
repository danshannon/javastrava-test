package test.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.Strava;
import javastrava.cache.StravaCache;
import javastrava.cache.impl.StravaCacheImpl;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class CacheTest extends APITest {
	private StravaCache<StravaActivity, Integer> activityCache(final boolean populate) throws Exception {
		final Token token = TestUtils.getValidToken();
		final StravaCache<StravaActivity, Integer> cache = new StravaCacheImpl<StravaActivity, Integer>(
				StravaActivity.class, token);
		assertEquals(0, cache.size());
		if (!populate) {
			return cache;
		}
		final API api = new API(token);
		final StravaActivity[] activities = api.listAuthenticatedAthleteActivities(null, null, null, null);
		for (final StravaActivity activity : activities) {
			cache.put(activity);
		}
		return cache;
	}

	/**
	 * Generates and populates a cache of athletes
	 *
	 * @param token
	 *            The token to use
	 * @param populate
	 *            Should the returned cache be populated
	 * @return The cache, populated with athletes (list of friends of the
	 *         authenticated user) if required
	 * @throws Exception
	 */
	private StravaCache<StravaAthlete, Integer> athleteCache(final boolean populate) throws Exception {
		final Token token = TestUtils.getValidToken();
		final StravaCache<StravaAthlete, Integer> cache = new StravaCacheImpl<StravaAthlete, Integer>(
				StravaAthlete.class, token);
		assertEquals(0, cache.size());
		if (!populate) {
			return cache;
		}
		final API api = new API(token);
		final StravaAthlete[] athletes = api.listAthleteFriends(token.getAthlete().getId(), null, null);
		for (final StravaAthlete athlete : athletes) {
			cache.put(athlete);
		}
		return cache;
	}

	@Test
	public void testActivityCache() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaCache<StravaActivity, Integer> cache = activityCache(true);
			assertNotNull(cache);
			assertFalse(cache.size() == 0);
			cache = activityCache(false);
			assertEquals(0, cache.size());
		} );
	}

	/**
	 * Test that the athlete cache generator actually does generate a cache, and
	 * puts athletes in it
	 *
	 * @throws Exception
	 */
	@Test
	public void testAthleteCache() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaCache<StravaAthlete, Integer> cache = athleteCache(true);
			assertNotNull(cache);
			assertFalse(cache.size() == 0);
			cache = athleteCache(false);
			assertEquals(0, cache.size());
		} );
	}

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
		RateLimitedTestRunner.run(() -> {
			final StravaCache<StravaAthlete, Integer> cache = athleteCache(true);
			assertFalse(cache.size() == 0);
			cache.removeAll();
			assertEquals(0, cache.size());
		} );
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
		RateLimitedTestRunner.run(() -> {
			final StravaCache<StravaAthlete, Integer> cache1 = athleteCache(true);
			final StravaCache<StravaActivity, Integer> cache2 = activityCache(true);
			cache1.removeAll();
			assertEquals(0, cache1.size());
			assertFalse(cache2.size() == 0);
		} );
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
		RateLimitedTestRunner.run(() -> {
			final Token token1 = TestUtils.getValidToken();
			final API api1 = new API(token1);
			final StravaCache<StravaAthlete, Integer> cache1 = new StravaCacheImpl<StravaAthlete, Integer>(
					StravaAthlete.class, token1);
			final StravaAthlete[] athletes = api1.listAthleteFriends(token1.getAthlete().getId(), null, null);
			for (final StravaAthlete athlete : athletes) {
				cache1.put(athlete);
			}

			final Token token2 = TestUtils.getValidTokenWithFullAccess();
			final API api2 = new API(token1);
			final StravaCache<StravaAthlete, Integer> cache2 = new StravaCacheImpl<StravaAthlete, Integer>(
					StravaAthlete.class, token2);
			final StravaAthlete[] athletes2 = api2.listAthleteFriends(token1.getAthlete().getId(), null, null);
			for (final StravaAthlete athlete : athletes2) {
				cache2.put(athlete);
			}

			cache1.removeAll();
			assertFalse(0 == cache2.size());

		} );

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
		RateLimitedTestRunner.run(() -> {
			final StravaCache<StravaAthlete, Integer> cache = athleteCache(false);
			StravaAthlete athlete = api().getAuthenticatedAthlete();
			cache.put(athlete);
			athlete.setFirstname("Bob");
			final Integer id = athlete.getId();
			cache.put(athlete);
			assertEquals(1, cache.size());
			athlete = null;
			athlete = cache.get(id);
			assertNotNull(athlete);
			assertEquals("Bob", athlete.getFirstname());
		} );
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
		RateLimitedTestRunner.run(() -> {
			final StravaCache<StravaAthlete, Integer> cache = athleteCache(false);
			StravaAthlete athlete = api().getAuthenticatedAthlete();
			cache.put(athlete);
			athlete = cache.get(TestUtils.ATHLETE_INVALID_ID);
			assertNull(athlete);
		} );
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
		RateLimitedTestRunner.run(() -> {
			final StravaCache<StravaAthlete, Integer> cache = athleteCache(false);
			final StravaAthlete athlete = api().getAuthenticatedAthlete();
			cache.put(athlete);
			final StravaAthlete athleteCached = cache.get(TestUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(athleteCached);
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, athleteCached.getId());
		} );

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
		RateLimitedTestRunner.run(() -> {
			final StravaCache<StravaAthlete, Integer> cache = athleteCache(false);
			final StravaAthlete athlete = api().getAuthenticatedAthlete();
			cache.put(athlete);
			cache.removeAll();
			final StravaAthlete athleteCached = cache.get(TestUtils.ATHLETE_AUTHENTICATED_ID);
			assertNull(athleteCached);

		} );
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
		RateLimitedTestRunner.run(() -> {
			final StravaCache<StravaAthlete, Integer> cache = athleteCache(false);
			cache.put(null);
			assertEquals(0, cache.size());
		} );

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
		RateLimitedTestRunner.run(() -> {
			final Token token1 = TestUtils.getValidTokenWithFullAccess();
			final Token token2 = TestUtils.getValidToken();
			assertFalse(token1.equals(token2));
			final StravaCache<StravaAthlete, Integer> cache1 = new StravaCacheImpl<StravaAthlete, Integer>(
					StravaAthlete.class, token1);
			final StravaCache<StravaAthlete, Integer> cache2 = new StravaCacheImpl<StravaAthlete, Integer>(
					StravaAthlete.class, token2);
			cache1.put(token1.getAthlete());
			final StravaAthlete athlete = cache2.get(token1.getAthlete().getId());
			assertNull(athlete);
		} );

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
		RateLimitedTestRunner.run(() -> {
			final Token token = TestUtils.getValidToken();
			final StravaCache<StravaAthlete, Integer> cache1 = new StravaCacheImpl<StravaAthlete, Integer>(
					StravaAthlete.class, token);
			final StravaCache<StravaAthlete, Integer> cache2 = new StravaCacheImpl<StravaAthlete, Integer>(
					StravaAthlete.class, token);
			cache1.put(token.getAthlete());
			final StravaAthlete athlete = cache2.get(token.getAthlete().getId());
			assertNotNull(athlete);
			assertEquals(token.getAthlete().getId(), athlete.getId());
		} );

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
		RateLimitedTestRunner.run(() -> {
			final Token token = TestUtils.getValidToken();
			final StravaCache<StravaAthlete, Integer> cache = new StravaCacheImpl<StravaAthlete, Integer>(
					StravaAthlete.class, token);
			cache.put(token.getAthlete());
			final Strava strava = new Strava(token);
			strava.deauthorise(token);
			assertEquals(0, cache.size());
		} );

	}

}
