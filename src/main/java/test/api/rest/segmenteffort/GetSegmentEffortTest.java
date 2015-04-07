package test.api.rest.segmenteffort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.api.rest.APITest;
import test.issues.strava.Issue78;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetSegmentEffortTest extends APITest {
	@Test
	public void testGetSegmentEffort_invalid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Long id = TestUtils.SEGMENT_EFFORT_INVALID_ID;
			try {
				api().getSegmentEffort(id);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned a non-existent segment effort");
		});
	}

	@Test
	public void testGetSegmentEffort_private() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Long id = TestUtils.SEGMENT_EFFORT_PRIVATE_ID;
			final StravaSegmentEffort effort = api().getSegmentEffort(id);
			assertNotNull(effort);
			StravaSegmentEffortTest.validateSegmentEffort(effort, id, effort.getResourceState());
		});
	}

	@Test
	public void testGetSegmentEffort_privateOtherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Long id = TestUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID;
			try {
				api().getSegmentEffort(id);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Got segment effort for a private segment belonging to another user");
		});
	}

	/**
	 * Check that an effort on a private activity is not returned
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetSegmentEffort_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentEffort(5735858255L);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned segment effort for a private activity, without view_private");
		});
	}

	/**
	 * Check that an effort on a private activity is returned with view_private scope
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetSegmentEffort_privateActivityViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort effort = apiWithViewPrivate().getSegmentEffort(5735858255L);
			assertNotNull(effort);
			assertEquals(StravaResourceState.DETAILED, effort.getResourceState());
		});
	}

	/**
	 * Check that an effort on a private segment is not returned
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetSegmentEffort_privateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#78
			if (new Issue78().isIssue()) {
				return;
			}
			// End of workaround

			try {
				api().getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ID);
			} catch (final UnauthorizedException e) {
				return;
			}
			fail("Returned segment effort for a segment flagged private");
		});
	}

	// Test cases
	// 1. Valid effort
	// 2. Invalid effort
	// 3. Private effort which does belong to the current athlete (is returned)
	// 4. Private effort which doesn't belong to the current athlete (is not returned)
	@Test
	public void testGetSegmentEffort_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Long id = TestUtils.SEGMENT_EFFORT_VALID_ID;
			final StravaSegmentEffort effort = api().getSegmentEffort(id);
			assertNotNull(effort);
			StravaSegmentEffortTest.validateSegmentEffort(effort, id, effort.getResourceState());
		});
	}

}
