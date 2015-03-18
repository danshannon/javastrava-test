package test.issues.strava;

import static org.junit.Assert.fail;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.SegmentAPI;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.StravaInternalServerErrorException;

import org.junit.Test;

import test.utils.TestUtils;

public class Issue35 {
	@Test
	public void testIssue() throws NotFoundException {
		final SegmentAPI retrofit = API.instance(SegmentAPI.class, TestUtils.getValidToken());
		final String start = "2015-01-01T18:10:47";
		final String end = "9999-12-31T18:11:07";
		try {
			retrofit.listSegmentEfforts(966356, null, start, end, 9, 200);
		} catch (final StravaInternalServerErrorException e) {
			// Expected behaviour
			return;
		}
		fail("Didn't get the expected 500 internal server error");
	}
}
