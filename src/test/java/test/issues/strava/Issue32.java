package test.issues.strava;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.service.AthleteServices;
import javastrava.api.v3.service.impl.retrofit.AthleteServicesImpl;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue32 {
	@Test
	public void testIssue() {
		final List<StravaSegmentEffort> efforts = service().listAllAthleteKOMs(200384);
		assertNotNull(efforts);
		boolean issue = false;
		for (final StravaSegmentEffort effort : efforts) {
			StravaSegmentEffortTest.validateSegmentEffort(effort);
			if (!isKom(effort.getSegment(), 200384)) {
				issue = true;
			}
		}
		assertTrue(issue);

	}

	private boolean isKom(final StravaSegment segment, final Integer athleteId) {
		final StravaSegmentLeaderboard leaderboard = SegmentServicesImpl.implementation(TestUtils.getValidToken())
				.getSegmentLeaderboard(segment.getId());
		boolean isKom = false;
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			if (entry.getAthleteId().equals(athleteId) && entry.getRank().equals(1)) {
				isKom = true;
			}
		}
		return isKom;
	}

	private AthleteServices service() {
		return AthleteServicesImpl.implementation(TestUtils.getValidToken());
	}
}
