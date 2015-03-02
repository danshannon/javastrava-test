package test.api.service.impl.retrofit.segmentservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.SegmentServices;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.utils.TestUtils;

public class ListAllSegmentEffortsTest {
	@Test
	public void listAllSegmentEfforts_validSegment() {
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID);
		assertNotNull(efforts);
		for (StravaSegmentEffort effort : efforts) {
			StravaSegmentEffortTest.validateSegmentEffort(effort);
		}
	}
	
	@Test
	public void listAllSegmentEfforts_invalidSegment() {
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_INVALID_ID);
		assertNull(efforts);
		
	}
	
	@Test
	public void listAllSegmentEfforts_privateSegment() {
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_PRIVATE_ID);
		assertNotNull(efforts);
		for (StravaSegmentEffort effort : efforts) {
			StravaSegmentEffortTest.validateSegmentEffort(effort);
		}
		
	}
	
	@Test
	public void listAllSegmentEfforts_privateSegmentOtherUser() {
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
		assertNull(efforts);
		
	}
	
	// TODO Needs a Workaround!
	//	DO NOT RUN IT DOESN'T SEEM TO WORK AS EXPECTED	
	@Test
	public void listAllSegmentEfforts_hazardousSegment() {
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_HAZARDOUS_ID);
		assertNotNull(efforts);
		assertEquals(0,efforts.size());
		
	}
	
	@Test
	public void listAllSegmentEfforts_filterByValidAthlete() {
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_AUTHENTICATED_ID, null, null);
		assertNotNull(efforts);
		for (StravaSegmentEffort effort : efforts) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID,effort.getAthlete().getId());
			StravaSegmentEffortTest.validateSegmentEffort(effort);
		}
	}
	
	@Test
	public void listAllSegmentEfforts_filterByInvalidAthlete() {
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_INVALID_ID, null, null);
		assertNull(efforts);
	}
	
	@Test
	public void listAllSegmentEfforts_filterByBeforeDate() {
		Calendar beforeDate = Calendar.getInstance();
		beforeDate.set(2013,Calendar.JANUARY,1);
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, null, beforeDate);
		assertNotNull(efforts);
		for (StravaSegmentEffort effort : efforts) {
			assertTrue(effort.getStartDate().before(beforeDate.getTime()));
			StravaSegmentEffortTest.validateSegmentEffort(effort);
		}
	}

	@Test
	public void listAllSegmentEfforts_filterByAfterDate() {
		Calendar afterDate = Calendar.getInstance();
		afterDate.set(2015,Calendar.JANUARY,1);
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, afterDate, null);
		assertNotNull(efforts);
		for (StravaSegmentEffort effort : efforts) {
			assertTrue(effort.getStartDate().after(afterDate.getTime()));
			StravaSegmentEffortTest.validateSegmentEffort(effort);
		}
	}

	@Test
	public void listAllSegmentEfforts_filterByDateRange() {
		Calendar afterDate = Calendar.getInstance();
		afterDate.set(2013,Calendar.JANUARY,1);
		Calendar beforeDate = Calendar.getInstance();
		beforeDate.set(2013,Calendar.DECEMBER,31);
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, afterDate, beforeDate);
		assertNotNull(efforts);
		for (StravaSegmentEffort effort : efforts) {
			assertTrue(effort.getStartDate().after(afterDate.getTime()));
			assertTrue(effort.getStartDate().before(beforeDate.getTime()));
			StravaSegmentEffortTest.validateSegmentEffort(effort);
		}
	}
	
	@Test
	public void listAllSegmentEfforts_filterByEverything() {
		Calendar afterDate = Calendar.getInstance();
		afterDate.set(2013,Calendar.JANUARY,1,0,0,0);
		Calendar beforeDate = Calendar.getInstance();
		beforeDate.set(2013,Calendar.DECEMBER,31,23,59,59);
		List<StravaSegmentEffort> efforts = service().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_AUTHENTICATED_ID, afterDate, beforeDate);
		assertNotNull(efforts);
		for (StravaSegmentEffort effort : efforts) {
			assertTrue(effort.getStartDate().before(beforeDate.getTime()));
			assertTrue(effort.getStartDate().after(afterDate.getTime()));
			assertTrue(effort.getStartDate().before(beforeDate.getTime()));
			StravaSegmentEffortTest.validateSegmentEffort(effort);
		}
	}

	private SegmentServices service() {
		return SegmentServicesImpl.implementation(TestUtils.getValidToken());
	}
}
