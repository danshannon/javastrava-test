package test.service.impl.segmentservice;

import static org.junit.Assume.assumeFalse;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.service.Strava;
import test.issues.strava.Issue162;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.SegmentDataUtils;

/**
 * <p>
 * Specific tests and config for {@link Strava#starSegment(Integer, Boolean)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StarSegmentTest extends GetMethodTest<StravaSegment, Integer> {
	@Override
	protected Integer getIdInvalid() {
		return SegmentDataUtils.SEGMENT_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return SegmentDataUtils.SEGMENT_PRIVATE_ID;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	@Override
	protected Integer getIdValid() {
		return SegmentDataUtils.SEGMENT_VALID_ID;
	}

	@Override
	protected GetCallback<StravaSegment, Integer> getter() {
		return ((strava, id) -> strava.starSegment(id, Boolean.TRUE));
	}

	@Override
	protected void validate(StravaSegment result) {
		SegmentDataUtils.validateSegment(result);

	}

	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		assumeFalse(Issue162.isIssue);

		super.testPrivateWithNoViewPrivateScope();
	}

}
