package test.issues.strava;

import javastrava.model.StravaSegment;
import javastrava.model.StravaSegmentEffort;
import javastrava.model.StravaSegmentLeaderboard;
import javastrava.model.StravaSegmentLeaderboardEntry;
import javastrava.service.impl.SegmentServiceImpl;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue32 extends IssueTest {
	@SuppressWarnings("boxing")
	private static boolean isKom(final StravaSegment segment, final Integer athleteId) {
		final StravaSegmentLeaderboard leaderboard = SegmentServiceImpl.instance(TestUtils.getValidToken()).getSegmentLeaderboard(segment.getId());
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
