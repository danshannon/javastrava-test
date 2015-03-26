/**
 *
 */
package test.api.service.impl;

import static org.junit.Assert.assertTrue;
import javastrava.api.v3.service.impl.StravaServiceImpl;
import javastrava.config.StravaConfig;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaServiceImplTest {
	@Test
	public void testRequestRateDailyPercentageExceeded() {
		StravaServiceImpl.requestRateDaily = StravaConfig.RATE_LIMIT_DAILY + 1;
		assertTrue(100f < StravaServiceImpl.requestRateDailyPercentage());
	}

	@Test
	public void testRequestRateDailyPercentageWarning() {
		StravaServiceImpl.requestRateDaily = StravaConfig.RATE_LIMIT_DAILY;
		assertTrue(100f == StravaServiceImpl.requestRateDailyPercentage());
	}

	@Test
	public void testRequestRatePercentageExceeded() {
		StravaServiceImpl.requestRate = StravaConfig.RATE_LIMIT + 1;
		assertTrue(100f < StravaServiceImpl.requestRatePercentage());
	}

	@Test
	public void testRequestRatePercentageWarning() {
		StravaServiceImpl.requestRate = StravaConfig.RATE_LIMIT;
		assertTrue(100f == StravaServiceImpl.requestRatePercentage());
	}
}
