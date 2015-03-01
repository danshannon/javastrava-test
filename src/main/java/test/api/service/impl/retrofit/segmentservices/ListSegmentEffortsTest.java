package test.api.service.impl.retrofit.segmentservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.SegmentServices;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.api.service.impl.ListCallback;
import test.api.service.impl.PagingListMethodTest;
import test.utils.TestUtils;

public class ListSegmentEffortsTest extends PagingListMethodTest<StravaSegmentEffort, Long>{
	// Test cases
	// 1. No filtering, valid segment
	@Test
	public void testListSegmentEfforts_validSegment() {
		final SegmentServices service = service();
		final List<StravaSegmentEffort> efforts = service.listSegmentEfforts(TestUtils.SEGMENT_VALID_ID);
		assertNotNull(efforts);
		assertFalse(efforts.size() == 0);
		validateList(efforts);
	}

	// 2. No filtering, invalid segment
	@Test
	public void testListSegmentEfforts_invalidSegment() {
		final SegmentServices service = service();
		final List<StravaSegmentEffort> efforts = service.listSegmentEfforts(TestUtils.SEGMENT_INVALID_ID);
		assertNull(efforts);
	}

	// 3. Filter by valid athlete, valid segment
	@Test
	public void testListSegmentEfforts_filterByValidAthlete() {
		final SegmentServices service = service();
		final List<StravaSegmentEffort> efforts = service.listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_AUTHENTICATED_ID, null, null);
		assertNotNull(efforts);
		assertFalse(0 == efforts.size());
		for (final StravaSegmentEffort effort : efforts) {
			validate(effort);
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
		}
	}

	// 4. Filter by invalid athlete, valid segment
	@Test
	public void testListSegmentEfforts_filterByInvalidAthlete() {
		final SegmentServices service = service();
		final List<StravaSegmentEffort> efforts = service.listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_INVALID_ID, null, null);
		assertNull(efforts);
	}

	// 5. Filter by start date, valid segment
	@Test
	public void testListSegmentEfforts_filterByStartDate() {
		final SegmentServices service = service();
		final Calendar startDate = Calendar.getInstance();
		startDate.set(2014, Calendar.JANUARY, 1, 0, 0, 0);

		final List<StravaSegmentEffort> efforts = service.listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, startDate, null);
		assertNotNull(efforts);
		assertFalse(0 == efforts.size());
		for (final StravaSegmentEffort effort : efforts) {
			assertNotNull(effort.getStartDateLocal());
			assertTrue(effort.getStartDateLocal().after(startDate.getTime()));
			validate(effort);
		}

	}

	// 6. Filter by end date, valid segment
	@Test
	public void testListSegmentEfforts_filterByEndDate() {
		final SegmentServices service = service();
		final Calendar endDate = Calendar.getInstance();
		endDate.set(2013, Calendar.DECEMBER, 31, 23, 59, 59);

		final List<StravaSegmentEffort> efforts = service.listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, null, endDate);
		assertNotNull(efforts);
		assertFalse(0 == efforts.size());
		for (final StravaSegmentEffort effort : efforts) {
			assertNotNull(effort.getStartDateLocal());
			assertTrue(effort.getStartDateLocal().before(endDate.getTime()));
			validate(effort);
		}
	}

	// 7. Filter by date range, valid segment
	@Test
	public void testListSegmentEfforts_filterByDateRange() {
		final SegmentServices service = service();
		final Calendar startDate = Calendar.getInstance();
		startDate.set(2014, Calendar.JANUARY, 1, 0, 0, 0);
		final Calendar endDate = Calendar.getInstance();
		endDate.set(2014, Calendar.JANUARY, 31, 23, 59, 59);

		final List<StravaSegmentEffort> efforts = service.listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, startDate, endDate);
		assertNotNull(efforts);
		assertFalse(0 == efforts.size());
		for (final StravaSegmentEffort effort : efforts) {
			assertNotNull(effort.getStartDateLocal());
			assertTrue(effort.getStartDateLocal().after(startDate.getTime()));
			assertTrue(effort.getStartDateLocal().before(endDate.getTime()));
			validate(effort);
		}
	}

	private SegmentServices service() {
		return SegmentServicesImpl.implementation(TestUtils.getValidToken());
	}

	@Override
	protected void validate(final StravaSegmentEffort effort, final Long id, final StravaResourceState state) {
		StravaSegmentEffortTest.validateSegmentEffort(effort, id, state);

	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		StravaSegmentEffortTest.validateSegmentEffort(effort);

	}

	@Override
	protected ListCallback<StravaSegmentEffort> callback() {
		return (new ListCallback<StravaSegmentEffort>() {

			@Override
			public List<StravaSegmentEffort> getList(final Paging paging) {
				return service().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, paging);
			}

		});
	}

}
