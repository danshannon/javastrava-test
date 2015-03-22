/**
 *
 */
package test.utils;

import javastrava.api.v3.service.Strava;
import javastrava.api.v3.service.exception.StravaAPIRateLimitException;

/**
 * @author danshannon
 *
 */
public class RateLimitedTestRunner {
	public static void run(final TestCallback t) throws Exception {
		boolean loop = true;
		while (loop) {
			try {
				t.test();
				loop = false;
			} catch (final StravaAPIRateLimitException e) {
				waitForRateLimit();
			}
		}
	}

	private static void waitForRateLimit() {
		boolean loop = true;
		final Strava strava = TestUtils.strava();
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
