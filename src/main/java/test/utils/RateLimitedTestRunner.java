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
		int loopCount = 0;
		final int maxLoops = 10;
		long delayMilliseconds = 15000;
		while (loopCount < (maxLoops + 1)) {
			try {
				loopCount++;
				delayMilliseconds = delayMilliseconds * 2;
				log.debug("RateLimitedTestRunner.run, loopCount = " + loopCount + ", maxLoops = " + maxLoops + ", delay = " + (delayMilliseconds / 1000)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				t.test();
				loopCount = maxLoops + 1;
			} catch (final StravaAPIRateLimitException e) {
				if (loopCount == maxLoops) {
					throw e;
				}
				log.error("Rate limit exceeded x" + loopCount + " - pausing test execution for " + (delayMilliseconds / 1000) + " seconds"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				Thread.sleep(delayMilliseconds);
			} catch (final StravaServiceUnavailableException e) {
				if (loopCount == maxLoops) {
					throw e;
				}
				log.error("Strava temporarily unavailable (503 error) - pausing execution for " + (delayMilliseconds / 1000) + " seconds"); //$NON-NLS-1$ //$NON-NLS-2$
				Thread.sleep(delayMilliseconds);
			} catch (final StravaAPINetworkException e) {
				if (loopCount == maxLoops) {
					throw e;
				}
				log.error("Network failure - pausing test for " + (delayMilliseconds / 1000) + " seconds"); //$NON-NLS-1$ //$NON-NLS-2$
				Thread.sleep(delayMilliseconds);
			}

		}
	}

}
