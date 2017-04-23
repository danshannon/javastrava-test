/**
 *
 */
package test.service.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javastrava.api.v3.service.Strava;

/**
 * <p>
 * Tests for Strava rate limiting aspects
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaServiceImplTest {
	/**
	 * Check that the calculation of daily rate limit excession works
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRequestRateDailyPercentageExceeded() {
		Strava.REQUEST_RATE_DAILY = Strava.RATE_LIMIT_DAILY + 1;
		assertTrue(100f < Strava.requestRateDailyPercentage());
	}

	/**
	 * Check that the calculation of daily rate limit excession works
	 */
	@SuppressWarnings("static-method")
	@Test
	/**
	 * Check that the calculation of rate limit excession works
	 */
	public void testRequestRateDailyPercentageWarning() {
		Strava.REQUEST_RATE_DAILY = Strava.RATE_LIMIT_DAILY;
		assertTrue(100f == Strava.requestRateDailyPercentage());
	}

	/**
	 * Check that the calculation of rate limit excession works
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRequestRatePercentageExceeded() {
		Strava.REQUEST_RATE_CURRENT = Strava.RATE_LIMIT_CURRENT + 1;
		assertTrue(100f < Strava.requestRateCurrentPercentage());
	}

	/**
	 * Check that the calculation of daily rate limit excession works
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRequestRatePercentageWarning() {
		Strava.REQUEST_RATE_CURRENT = Strava.RATE_LIMIT_CURRENT;
		assertTrue(100f == Strava.requestRateCurrentPercentage());
	}
}
