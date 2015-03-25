package test.issues.strava;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.service.AthleteService;
import javastrava.api.v3.service.impl.AthleteServiceImpl;
import javastrava.api.v3.service.impl.SegmentServiceImpl;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue32 {
	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
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
		});
	}

	private boolean isKom(final StravaSegment segment, final Integer athleteId) {
		final StravaSegmentLeaderboard leaderboard = SegmentServiceImpl.instance(TestUtils.getValidToken()).getSegmentLeaderboard(segment.getId());
		boolean isKom = false;
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			if (entry.getAthleteId().equals(athleteId) && entry.getRank().equals(1)) {
				isKom = true;
			}
		}
		return isKom;
	}

	private AthleteService service() {
		return AthleteServiceImpl.instance(TestUtils.getValidToken());
	}
}
