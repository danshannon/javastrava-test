package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import javastrava.api.v3.model.StravaSegmentEffort;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class ListAllSegmentEffortsTest extends StravaTest {
	@Test
	public void listAllSegmentEfforts_validSegment() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID);
				assertNotNull(efforts);
				for (final StravaSegmentEffort effort : efforts) {
					StravaSegmentEffortTest.validateSegmentEffort(effort);
				}
			}
		});
	}

	@Test
	public void listAllSegmentEfforts_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_INVALID_ID);
				assertNull(efforts);
			}
		});
	}

	@Test
	public void listAllSegmentEfforts_privateSegment() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_PRIVATE_ID);
				assertNotNull(efforts);
				for (final StravaSegmentEffort effort : efforts) {
					StravaSegmentEffortTest.validateSegmentEffort(effort);
				}
			}
		});
	}

	@Test
	public void listAllSegmentEfforts_privateSegmentOtherUser() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
				assertNotNull(efforts);
				assertTrue(efforts.isEmpty());
			}
		});
	}

	@Test
	public void listAllSegmentEfforts_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_HAZARDOUS_ID);
				assertNotNull(efforts);
				assertEquals(0, efforts.size());
			}
		});
	}

	@Test
	public void listAllSegmentEfforts_filterByValidAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_AUTHENTICATED_ID, null,
						null);
				assertNotNull(efforts);
				for (final StravaSegmentEffort effort : efforts) {
					assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
					StravaSegmentEffortTest.validateSegmentEffort(effort);
				}
			}
		});
	}

	@Test
	public void listAllSegmentEfforts_filterByInvalidAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_INVALID_ID, null, null);
				assertNull(efforts);
			}
		});
	}

	@Test
	public void listAllSegmentEfforts_filterByBeforeDate() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final LocalDateTime beforeDate = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, null, beforeDate);
				assertNotNull(efforts);
				for (final StravaSegmentEffort effort : efforts) {
					assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
					StravaSegmentEffortTest.validateSegmentEffort(effort);
				}
			}
		});
	}

	@Test
	public void listAllSegmentEfforts_filterByAfterDate() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final LocalDateTime afterDate = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, afterDate, null);
				assertNotNull(efforts);
				for (final StravaSegmentEffort effort : efforts) {
					assertTrue(effort.getStartDateLocal().isAfter(afterDate));
					StravaSegmentEffortTest.validateSegmentEffort(effort);
				}
			}
		});
	}

	@Test
	public void listAllSegmentEfforts_filterByDateRange() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final LocalDateTime afterDate = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
				final LocalDateTime beforeDate = LocalDateTime.of(2013, Month.DECEMBER, 1, 0, 0);
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, afterDate, beforeDate);
				assertNotNull(efforts);
				for (final StravaSegmentEffort effort : efforts) {
					assertTrue(effort.getStartDateLocal().isAfter(afterDate));
					assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
					StravaSegmentEffortTest.validateSegmentEffort(effort);
				}
			}
		});
	}

	@Test
	public void listAllSegmentEfforts_filterByEverything() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final LocalDateTime afterDate = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
				final LocalDateTime beforeDate = LocalDateTime.of(2013, Month.DECEMBER, 1, 0, 0);
				final List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_AUTHENTICATED_ID,
						afterDate, beforeDate);
				assertNotNull(efforts);
				for (final StravaSegmentEffort effort : efforts) {
					assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
					assertTrue(effort.getStartDateLocal().isAfter(afterDate));
					assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
					StravaSegmentEffortTest.validateSegmentEffort(effort);
				}
			}
		});
	}

}
