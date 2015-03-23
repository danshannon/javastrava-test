/**
 *
 */
package test.utils;

import javastrava.api.v3.service.Strava;
import javastrava.api.v3.service.exception.StravaAPIRateLimitException;
import javastrava.api.v3.service.exception.StravaServiceUnavailableException;

/**
 * @author danshannon
 *
 */
public class RateLimitedTestRunner {
	private static Strava strava = null;

	static {
		try {
			strava = TestUtils.strava();
		} catch (final StravaServiceUnavailableException e) {
			waitForServiceRestoration();
		}
	}

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
			}
		}
	}

	/**
	 *
	 */
	private static void waitForServiceRestoration() {
		boolean loop = true;
		while (loop) {
			try {
				System.out.println("WARN - Strava temporarily unavailable (503 error) - pausing execution for 60 seconds");
				Thread.sleep(60000l);
			} catch (final InterruptedException e) {
				// ignore
			}
			try {
				strava = TestUtils.strava();
				strava.getAuthenticatedAthlete();
				// If the call works, then we didn't get a service unavailable exception so we're good to go
				loop = false;
			} catch (final StravaServiceUnavailableException e) {
				loop = true;
			}
		}

	}

	private static void waitForRateLimit() {
		boolean loop = true;
		while (loop) {
			try {
				System.out.println("WARN - Rate limit exceeded - pausing test execution for 15 seconds");
				Thread.sleep(15000l);
			} catch (final InterruptedException e) {
				// ignore
			}
			try {
				strava.getAuthenticatedAthlete();
				// If the call to Strava works then we didn't get a rate limit exception so we're good to go
				loop = false;
			} catch (final StravaAPIRateLimitException e) {
				loop = true;
			}
		}
	}
}
