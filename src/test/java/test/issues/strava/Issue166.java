package test.issues.strava;

import java.util.List;

import javastrava.model.StravaActivity;
import javastrava.util.Paging;
import test.utils.TestUtils;

/**
 * <p>
 * Issue test for issue #166 - see https://github.com/danshannon/javastravav3api/issues/166
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class Issue166 extends IssueTest {
	/**
	 * <code>true</code> if the issue is still current
	 */
	public static final boolean issue = Issue166.issue();

	/**
	 * @return <code>true</code> if the issue is still current
	 */
	public static boolean issue() {
		try {
			return new Issue166().isIssue();
		} catch (final Exception e) {
			return false;
		}
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		final List<StravaActivity> list = TestUtils.strava().listRecentClubActivities(2130, new Paging(1, 200));
		if ((list != null) && (list.size() == 200)) {
			return false;
		}
		return true;
	}

	@Override
	public int issueNumber() {
		return 166;
	}

	@Override
	public boolean isIntermittent() {
		return true;
	}

}
