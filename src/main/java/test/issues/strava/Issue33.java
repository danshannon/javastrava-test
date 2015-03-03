package test.issues.strava;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.SegmentServices;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;

import org.junit.Test;

import test.utils.TestUtils;

public class Issue33 {
	/**
	 * This test will PASS if issue 33 is still an issue
	 */
	@Test
	public void testIssue33() {
		final List<StravaSegmentEffort> efforts = service().listSegmentEfforts(TestUtils.SEGMENT_HAZARDOUS_ID);
		assertNotNull(efforts);
		assertFalse(0 == efforts.size());
	}

	private SegmentServices service() {
		return SegmentServicesImpl.implementation(TestUtils.getValidToken());
	}

}
