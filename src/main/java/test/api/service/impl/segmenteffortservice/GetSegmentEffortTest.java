package test.api.service.impl.segmenteffortservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class GetSegmentEffortTest extends StravaTest {
	// Test cases
	// 1. Valid effort
	// 2. Invalid effort
	// 3. Private effort which does belong to the current athlete (is returned)
	// 4. Private effort which doesn't belong to the current athlete (is not returned)
	@Test
	public void testGetSegmentEffort_valid() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final Long id = TestUtils.SEGMENT_EFFORT_VALID_ID;
				final StravaSegmentEffort effort = service().getSegmentEffort(id);
				assertNotNull(effort);
				StravaSegmentEffortTest.validateSegmentEffort(effort, id, effort.getResourceState());
			}
		});
	}

	@Test
	public void testGetSegmentEffort_invalid() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final Long id = TestUtils.SEGMENT_EFFORT_INVALID_ID;
				final StravaSegmentEffort effort = service().getSegmentEffort(id);
				assertNull(effort);
			}
		});
	}

	@Test
	public void testGetSegmentEffort_private() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final Long id = TestUtils.SEGMENT_EFFORT_PRIVATE_ID;
				final StravaSegmentEffort effort = service().getSegmentEffort(id);
				assertNotNull(effort);
				StravaSegmentEffortTest.validateSegmentEffort(effort, id, effort.getResourceState());
			}
		});
	}

	@Test
	public void testGetSegmentEffort_privateOtherAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final Long id = TestUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID;
				final StravaSegmentEffort effort = service().getSegmentEffort(id);
				assertNotNull(effort);
				StravaSegmentEffortTest.validateSegmentEffort(effort, id, effort.getResourceState());
				final StravaSegmentEffort comparison = new StravaSegmentEffort();
				comparison.setId(id);
				comparison.setResourceState(StravaResourceState.META);
				assertEquals(comparison, effort);
			}
		});
	}

}
