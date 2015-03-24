/**
 *
 */
package test.api.service.impl.activityservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListAllActivityKudoersTest extends StravaTest {
	@Test
	public void testListAllActivityKudoers_validActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = service().listAllActivityKudoers(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertNotNull(athletes);
			assertFalse(athletes.isEmpty());
			for (final StravaAthlete athlete : athletes) {
				StravaAthleteTest.validateAthlete(athlete);
			}
		});
	}

	@Test
	public void testListAllActivityKudoers_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = service().listAllActivityKudoers(TestUtils.ACTIVITY_INVALID);
			assertNull(athletes);
		});
	}

	@Test
	public void testListAllActivityKudoers_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = service().listAllActivityKudoers(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
			assertNotNull(athletes);
			assertTrue(athletes.isEmpty());
		});
	}
}
