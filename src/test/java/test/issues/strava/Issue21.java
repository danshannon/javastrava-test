package test.issues.strava;

import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.StreamAPI;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * These tests should PASS if issue <a href="https://github.com/danshannon/javastravav3api/issues/21">javastrava-api #21</a> is still current
 * </p>
 * 
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/21">https://github.com/danshannon/javastravav3api/issues/21</a>
 */
public class Issue21 {
	@Test
	public void testIssue() throws UnauthorizedException, NotFoundException, BadRequestException {
		StreamAPI retrofit = API.instance(StreamAPI.class, TestUtils.getValidToken());
		StravaStream[] streams = retrofit.getActivityStreams(245713183, StravaStreamType.DISTANCE.toString(), null, null);
		boolean issue = false;
		for (StravaStream stream : streams) {
			if (stream.getResolution() != null) {
				issue = true;
			}
		}
		assertTrue(issue);
	}
}
