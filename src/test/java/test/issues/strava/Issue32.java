package test.issues.strava;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.service.impl.SegmentServiceImpl;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue32 extends IssueTest {
	@SuppressWarnings("boxing")
	private static boolean isKom(final StravaSegment segment, final Integer athleteId) {
		final StravaSegmentLeaderboard leaderboard = SegmentServiceImpl.instance(TestUtils.getValidToken())
				.getSegmentLeaderboard(segment.getId());
		boolean isKom = false;
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			if (entry.getAthleteId().equals(athleteId) && entry.getRank().equals(1)) {
				isKom = true;
			}
		}
		return isKom;
	}

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		final StravaSegmentEffort[] efforts = this.api.listAthleteKOMs(200384, 1, 200);
		for (final StravaSegmentEffort effort : efforts) {
			if (!isKom(effort.getSegment(), 200384)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 32;
	}
}
