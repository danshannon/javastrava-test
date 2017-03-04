/**
 *
 */
package test.issues.strava;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;

/**
 * @author Dan Shannon
 *
 */
public class Issue89 extends IssueTest {

	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		final StravaStream[] streams = this.api.getSegmentStreams(612630,
				"time,latlng,distance,altitude,velocity_smooth,heartrate,cadence,watts,temp,moving,grade_smooth", //$NON-NLS-1$
				StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.TIME);
		if ((streams != null) && (streams.length != 0)) {
			return true;
		}
		return false;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 89;
	}

}
