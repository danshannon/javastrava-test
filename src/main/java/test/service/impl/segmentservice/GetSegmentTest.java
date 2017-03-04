package test.service.impl.segmentservice;

import javastrava.api.v3.model.StravaSegment;
import test.api.model.StravaSegmentTest;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.SegmentDataUtils;

/**
 * <p>
 * Specific tests for getSegment methods
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class GetSegmentTest extends GetMethodTest<StravaSegment, Integer> {

	@Override
	protected Integer getIdValid() {
		return SegmentDataUtils.SEGMENT_VALID_ID;
	}

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
	protected GetCallback<StravaSegment, Integer> getter() throws Exception {
		return ((strava, id) -> strava.getSegment(id));
	}

	@Override
	protected void validate(StravaSegment object) {
		StravaSegmentTest.validateSegment(object);
	}

}
