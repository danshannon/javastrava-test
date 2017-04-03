/**
 *
 */
package test.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javastrava.api.v3.service.exception.StravaAPINetworkException;
import javastrava.api.v3.service.exception.StravaAPIRateLimitException;
import javastrava.api.v3.service.exception.StravaServiceUnavailableException;

/**
 * @author Dan Shannon
 *
 */
public class RateLimitedTestRunner {
	private static Logger log = LogManager.getLogger();

	/**
	 * <p>
	 * Runs a test method (the callback provided) and handles Strava rate limiting, Strava outages, or network outages gracefully, therefore ensuring that the test gets run regardless of transient
	 * issues on the network
	 * </p>
	 *
	 * @param t
	 *            TestCallback which is used to run the test
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	public static void run(final TestCallback t) throws Exception {
		boolean loop = true;
		while (loop) {
			try {
				t.test();
				loop = false;
			} catch (final StravaAPIRateLimitException e) {
				waitForRateLimit();
			} catch (final StravaServiceUnavailableException e) {
				waitForServiceRestoration();
			} catch (final StravaAPINetworkException e) {
				waitForNetworkRestoration();
			}

		}
	}

	/**
	 * Wait until network connection to Strava is restored
	 */
	private static void waitForNetworkRestoration() {
		boolean loop = true;
		while (loop) {
			try {
				log.error("Network failure - pausing test for 15 seconds"); //$NON-NLS-1$
				Thread.sleep(15000l);
			} catch (final InterruptedException e) {
				// ignore
			}
			try {
				TestUtils.strava().getAuthenticatedAthlete();
				// If this call worked, we don't have network issues now
				loop = false;
			} catch (final StravaAPINetworkException e) {
				loop = true;
			}
		}

	}

	/**
	 * Wait until Strava rate limiting resets
	 */
	private static void waitForRateLimit() {
		boolean loop = true;
		while (loop) {
			try {
				log.error("Rate limit exceeded - pausing test execution for 2 minutes"); //$NON-NLS-1$
				Thread.sleep(120000L);
			} catch (final InterruptedException e) {
				// ignore
			}
			try {
				TestUtils.strava().getAuthenticatedAthlete();
				// If the call to Strava works then we didn't get a rate limit exception so we're good to go
				loop = false;
			} catch (final StravaAPIRateLimitException e) {
				loop = true;
			}
		}
	}

	/**
	 * Wait for Strava service outage
	 */
	private static void waitForServiceRestoration() {
		boolean loop = true;
		while (loop) {
			try {
				log.error("Strava temporarily unavailable (503 error) - pausing execution for 60 seconds"); //$NON-NLS-1$
				Thread.sleep(60000l);
			} catch (final InterruptedException e) {
				// ignore
			}
			try {
				TestUtils.strava().getAuthenticatedAthlete();
				// If the call works, then we didn't get a service unavailable exception so we're good to go
				loop = false;
			} catch (final StravaServiceUnavailableException e) {
				loop = true;
			}
		}

	}
}
