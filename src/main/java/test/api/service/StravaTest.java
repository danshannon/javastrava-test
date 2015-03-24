package test.api.service;

import javastrava.api.v3.service.Strava;
import test.utils.TestUtils;

/**
 * @author dshannon
 *
 */
public class StravaTest {
	protected Strava service() {
		return TestUtils.strava();
	}

	protected Strava serviceWithWriteAccess() {
		return TestUtils.stravaWithWriteAccess();
	}

}
